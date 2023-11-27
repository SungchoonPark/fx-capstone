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
            System.out.println("id = " + loginId);
            System.out.println("password = " + password);
            System.out.println("email = " + email);
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
}
