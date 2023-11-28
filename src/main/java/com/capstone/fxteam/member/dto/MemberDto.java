package com.capstone.fxteam.member.dto;

import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.model.enums.Role;
import lombok.*;

public class MemberDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequestDto {
        private String loginId;
        private String password;
        private String email;
        private String nickname;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .password(password)
                    .email(email)
                    .role(Role.ROLE_USER)
                    .nickname(nickname)
                    .provider(null)
                    .providerId(0)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpResponseDto {
        private String nickname;

        public SignUpResponseDto(Member member) {
            this.nickname = member.getNickname();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckIdDuplicationRequestDto {
        private String loginId;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckEmailDuplicationRequestDto {

        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckNicknameDuplicationRequestDto {

        private String nickname;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckDuplicationResponseDto {
        private boolean isDuplication;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInRequestDto {
        private String loginId;
        private String password;

        public Member toEntity() {
            return Member.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInResponseDto {
        private String accessToken;
        private String refreshToken;
        private Long accessTokenExpirationTime;

        public static SignInResponseDto toDto(String accessToken, String refreshToken, long accessTokenExpirationTime) {
            return SignInResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpirationTime(accessTokenExpirationTime)
                    .build();
        }
    }
}
