package com.capstone.fxteam.member.service;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.repository.MemberRepository;
import com.capstone.fxteam.security.JwtUtils;
import com.capstone.fxteam.security.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    public MemberDto.SignUpResponseDto signUp(MemberDto.SignUpRequestDto signUpRequestDto) {
        signUpRequestDto.setPassword(encodingPassword(signUpRequestDto.getPassword()));
        Member signUpMember = signUpRequestDto.toEntity();

        memberRepository.save(signUpMember);

        return MemberDto.SignUpResponseDto.builder()
                .nickname(signUpMember.getNickname())
                .build();
    }
    @Override
    public MemberDto.CheckDuplicationResponseDto checkIdDuplication(MemberDto.CheckIdDuplicationRequestDto idDuplicationRequestDto) {
        Optional<Member> memberByLoginId = memberRepository.findByLoginId(idDuplicationRequestDto.getLoginId());
        return isEmpty(memberByLoginId);
    }

    @Override
    public MemberDto.CheckDuplicationResponseDto checkEmailDuplication(MemberDto.CheckEmailDuplicationRequestDto emailDuplicationRequestDto) {
        Optional<Member> memberByEmail = memberRepository.findByEmail(emailDuplicationRequestDto.getEmail());

        return isEmpty(memberByEmail);
    }

    @Override
    public MemberDto.CheckDuplicationResponseDto checkNicknameDuplication(MemberDto.CheckNicknameDuplicationRequestDto nicknameDuplicationRequestDto) {
        Optional<Member> memberByNickname = memberRepository.findByNickname(nicknameDuplicationRequestDto.getNickname());

        return isEmpty(memberByNickname);

    }

    @Override
    public MemberDto.SignInResponseDto signIn(MemberDto.SignInRequestDto signDto) {
        Optional<Member> findMember = memberRepository.findByLoginId(signDto.getLoginId());

        /***
         * id가 틀린 경우
         */
        if(findMember.isEmpty()) {
            throw new CustomException(CustomResponseStatus.LOGIN_FAILED_LOGINID);
        }

        /***
         * id는 맞았지만 password가 틀린 경우
         */
        System.out.println("signDto password = " + signDto.getPassword());
        System.out.println("findMember = " + findMember.get().getPassword());
        if(!passwordEncoder.matches(signDto.getPassword(), findMember.get().getPassword())) {
            throw new CustomException(CustomResponseStatus.LOGIN_FAILED_PWD);
        }

        String accessToken = jwtUtils.createToken(findMember.get().getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + findMember.get().getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(findMember.get().getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            log.info("newRefreshToken : "+newRefreshToken);
            redisUtils.setDataExpire("RT:" + findMember.get().getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }

        return MemberDto.SignInResponseDto.toDto(accessToken, refreshToken, JwtUtils.TOKEN_VALID_TIME);
    }

    private MemberDto.CheckDuplicationResponseDto isEmpty(Optional<Member> member) {
        if (member.isEmpty()) {
            return MemberDto.CheckDuplicationResponseDto.builder()
                    .isDuplication(false)
                    .build();
        }
        return MemberDto.CheckDuplicationResponseDto.builder()
                .isDuplication(true)
                .build();
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
