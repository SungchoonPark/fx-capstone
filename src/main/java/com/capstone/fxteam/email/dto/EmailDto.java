package com.capstone.fxteam.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmailDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailRequestDto {
        String email;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailVerificationRequestDto {
        String email;
        String authCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailVerificationResultDto {
        boolean isVerify;
        public static EmailVerificationResultDto from(boolean isVerify) {
            return new EmailVerificationResultDto(isVerify);
        }
    }
}
