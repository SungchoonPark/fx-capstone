package com.capstone.fxteam.member.service;

import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.model.enums.Role;
import com.capstone.fxteam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto.SignUpResponseDto signUp(MemberDto.SignUpRequestDto signUpRequestDto) {
        log.info("Enter the signup controller");
        Member signUpMember = signUpRequestDto.toEntity();
        log.info("signUpMember : "+ signUpMember.getNickname());

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
}
