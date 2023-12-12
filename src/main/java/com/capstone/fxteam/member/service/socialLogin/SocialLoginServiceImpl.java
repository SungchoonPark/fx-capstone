package com.capstone.fxteam.member.service.socialLogin;

import com.capstone.fxteam.member.dto.MemberDto;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.model.enums.OAuthProvider;
import com.capstone.fxteam.member.model.enums.Role;
import com.capstone.fxteam.member.repository.MemberRepository;
import com.capstone.fxteam.security.JwtUtils;
import com.capstone.fxteam.security.RedisUtils;
import com.capstone.fxteam.security.oauth.OAuthInfoResponse;
import com.capstone.fxteam.security.oauth.OAuthLoginParams;
import com.capstone.fxteam.security.oauth.RequestOAuthInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginServiceImpl implements SocialLoginService{
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto.SignInResponseDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Member findOrCreateMember = findOrCreateMember(oAuthInfoResponse);
        String accessToken = jwtUtils.createToken(findOrCreateMember.getEmail(), JwtUtils.TOKEN_VALID_TIME);
        String refreshToken = redisUtils.getData("RT:" + findOrCreateMember.getEmail());

        if (refreshToken == null) {
            // refreshToken이 존재하지 않는다면 설정해줘야함
            String newRefreshToken = jwtUtils.createToken(findOrCreateMember.getEmail(), JwtUtils.REFRESH_TOKEN_VALID_TIME);
            log.info("newRefreshToken : " + newRefreshToken);
            redisUtils.setDataExpire("RT:" + findOrCreateMember.getEmail(), newRefreshToken, JwtUtils.REFRESH_TOKEN_VALID_TIME_IN_REDIS);
            refreshToken = newRefreshToken;
        }

        return MemberDto.SignInResponseDto.toDto(accessToken, refreshToken, JwtUtils.TOKEN_VALID_TIME);
    }

    private Member findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Member newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member newMember = Member.builder()
                .loginId(generateRandomLoginId())
                .password(generateRandomPassword())
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .role(Role.ROLE_USER)
                .provider(OAuthProvider.KAKAO)
                .build();

        return memberRepository.save(newMember);
    }

    private String generateRandomLoginId() {
        return RandomStringUtils.random(10, true, true);
    }

    private String generateRandomPassword() {
        return passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10));
    }
}
