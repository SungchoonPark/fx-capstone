package com.capstone.fxteam.member.service;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import com.capstone.fxteam.email.dto.EmailDto;
import com.capstone.fxteam.email.service.EmailService;
import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.repository.MemberRepository;
import com.capstone.fxteam.security.JwtUtils;
import com.capstone.fxteam.security.RedisUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final EmailService emailService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMills;

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
    public MemberDto.CheckDuplicationResponseDto checkIdDuplication(String id) {
        Optional<Member> memberByLoginId = memberRepository.findByLoginId(id);
        return isEmpty(memberByLoginId, "아이디");
    }

    @Override
    public MemberDto.CheckDuplicationResponseDto checkEmailDuplication(String email) {
        Optional<Member> memberByEmail = memberRepository.findByEmail(email);

        return isEmpty(memberByEmail, "이메일");
    }

    @Override
    public MemberDto.CheckDuplicationResponseDto checkNicknameDuplication(String nickname) {
        Optional<Member> memberByNickname = memberRepository.findByNickname(nickname);

        return isEmpty(memberByNickname, "닉네임");
    }

    @Override
    public MemberDto.SignInResponseDto signIn(MemberDto.SignInRequestDto signDto) {
        Member findMember = memberRepository.findByLoginId(signDto.getLoginId()).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.LOGIN_FAILED);
        });

        if (!passwordEncoder.matches(signDto.getPassword(), findMember.getPassword())) {
            throw new CustomException(CustomResponseStatus.LOGIN_FAILED);
        }

        String accessToken = jwtUtils.createToken(findMember.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + findMember.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(findMember.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            log.info("newRefreshToken : " + newRefreshToken);
            redisUtils.setDataExpire("RT:" + findMember.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }

        return MemberDto.SignInResponseDto.toDto(findMember.getNickname() ,accessToken, refreshToken, JwtUtils.TOKEN_VALID_TIME);
    }

    @Override
    public void logout(String accessToken) {
        String resolveToken = jwtUtils.resolveToken(accessToken);

        String emailInToken = jwtUtils.getEmailInToken(resolveToken);
        String refreshTokenInRedis = redisUtils.getData("RT:" + emailInToken);

        if (refreshTokenInRedis == null) {
            throw new CustomException(CustomResponseStatus.REFRESHTOKEN_NOT_FOUND);
        }

        redisUtils.deleteData("RT:" + emailInToken);

        redisUtils.setDataExpire(resolveToken, "logout", jwtUtils.getExpiration(resolveToken));
    }

    @Override
    public MemberDto.FindLoginIdResponseDto findLoginId(MemberDto.FindLoginIdRequestDto findLoginIdRequestDto) {
        return memberRepository.findByEmail(findLoginIdRequestDto.getEmail())
                .map(member -> MemberDto.FindLoginIdResponseDto.toDto(member.getLoginId()))
                .orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND));
    }

    @Override
    public void sendVerificationEmail(EmailDto.EmailRequestDto emailDto) {
        if (!isExistEmail(emailDto.getEmail())) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        String title = "F(x) - 이메일 인증안내";
        String authCode = createAuthCode();
        log.info("authCode Generated by createAuthCode() = " + authCode);
        emailService.sendEmail(emailDto.getEmail(), title, authCode);

        redisUtils.setDataExpire(AUTH_CODE_PREFIX + emailDto.getEmail(), authCode, authCodeExpirationMills);
    }

    @Override
    public EmailDto.EmailVerificationResultDto verifyAuthCode(EmailDto.EmailVerificationRequestDto emailVerificationDto) {
        if (!isExistEmail(emailVerificationDto.getEmail())) {
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        String authCodeInRedis = redisUtils.getData(AUTH_CODE_PREFIX + emailVerificationDto.getEmail());
        log.info("authCodeInRedis = " + authCodeInRedis);

        return EmailDto.EmailVerificationResultDto.from(emailVerificationDto.getAuthCode().equals(authCodeInRedis));
    }

    @Override
    public void forceChangePassword(MemberDto.forceChangePasswordRequestDto forceChangePasswordDto) {
        memberRepository.findByEmail(forceChangePasswordDto.getEmail())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND))
                .changePassword(encodingPassword(forceChangePasswordDto.getNewPassword()));
    }

    @Override
    public void normalChangePassword(MemberDto.normalChangePasswordRequestDto changePasswordDto, String username) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), member.getPassword())) {
            throw new CustomException(CustomResponseStatus.PASSWORD_NOT_MATCH);
        }

        member.changePassword(encodingPassword(changePasswordDto.getNewPassword()));
    }

    @Override
    public MemberDto.ReissueResponseDto tokenReissue(String refreshToken) {
        String resolveToken = jwtUtils.resolveToken(refreshToken);
        String emailInToken = jwtUtils.getEmailInToken(resolveToken);
        String refreshTokenInRedis = redisUtils.getData("RT:" + emailInToken);

        if(!resolveToken.equals(refreshTokenInRedis)) {
            throw new CustomException(CustomResponseStatus.REFRESHTOKEN_NOT_MATCH);
        }

        String newAccessToken = jwtUtils.createToken(emailInToken, JwtUtils.TOKEN_VALID_TIME);

        return MemberDto.ReissueResponseDto.from(newAccessToken);
    }

    private String createAuthCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(CustomResponseStatus.NO_SUCH_ALGORITHM);
        }
    }

    private boolean isExistEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    private MemberDto.CheckDuplicationResponseDto isEmpty(Optional<Member> member, String dupleCategory) {
        if (member.isEmpty()) {
            return MemberDto.CheckDuplicationResponseDto.builder()
                    .duplication(false)
                    .responseMessage("사용가능한 "+dupleCategory+"입니다.")
                    .build();
        }
        return MemberDto.CheckDuplicationResponseDto.builder()
                .duplication(true)
                .responseMessage("중복된 "+dupleCategory+"입니다.")
                .build();
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
