package com.capstone.fxteam.constant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ErrorDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntryPointErrorResponse{
        private String msg;
    }
}
