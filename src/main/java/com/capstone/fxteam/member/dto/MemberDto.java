package com.capstone.fxteam.member.dto;

import com.capstone.fxteam.constant.enums.DeleteEnum;
import com.capstone.fxteam.member.model.Member;
import com.capstone.fxteam.member.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class MemberDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequestDto {
        @NotBlank(message = "아이디가 공백입니다.")
        private String loginId;
        @NotBlank(message = "비밀번호가 공백입니다.")
        private String password;

        @Email(message = "email 형식에 맞게 작성해주세요.", regexp = "^.+\\.(com|net)$")
        @NotBlank(message = "이메일이 공백입니다.")
        private String email;
        @NotBlank(message = "닉네임이 공백입니다.")
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
                    .deleteEnum(DeleteEnum.NOT_DELETE)
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
        @NotBlank(message = "아이디가 공백입니다.")
        private String loginId;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckEmailDuplicationRequestDto {
        @NotBlank(message = "이메일이 공백입니다.")
        @Email(message = "email 형식에 맞게 작성해주세요.")
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckNicknameDuplicationRequestDto {
        @NotBlank(message = "닉네임이 공백입니다.")
        private String nickname;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckDuplicationResponseDto {
        private boolean duplication;
        private String responseMessage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInRequestDto {
        @NotBlank(message = "아이디가 공백입니다.")
        private String loginId;
        @NotBlank(message = "비밀번호가 공백입니다.")
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
        private String nickname;

        public static SignInResponseDto toDto(String nickname, String accessToken, String refreshToken, long accessTokenExpirationTime) {
            return SignInResponseDto.builder()
                    .nickname(nickname)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpirationTime(accessTokenExpirationTime)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindLoginIdRequestDto {
        @NotBlank(message = "이메일이 공백입니다.")
        @Email(message = "email 형식에 맞게 작성해주세요.")
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindLoginIdResponseDto {
        private String foundLoginId;

        public static FindLoginIdResponseDto toDto(String foundLoginId) {
            return FindLoginIdResponseDto.builder()
                    .foundLoginId(foundLoginId)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class forceChangePasswordRequestDto {
        @NotBlank(message = "이메일이 공백입니다.")
        @Email(message = "email 형식에 맞게 작성해주세요.")
        private String email;
        @NotBlank(message = "새 비밀번호가 공백입니다.")
        private String newPassword;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class normalChangePasswordRequestDto {
        @NotBlank(message = "기존 비밀번호가 공백입니다.")
        private String currentPassword;
        @NotBlank(message = "새 비밀번호가 공백입니다.")
        private String newPassword;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReissueResponseDto {
        private String newAccessToken;

        public static ReissueResponseDto from(String newAccessToken) {
            return new ReissueResponseDto(newAccessToken);
        }
    }
}
