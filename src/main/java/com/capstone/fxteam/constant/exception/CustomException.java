package com.capstone.fxteam.constant.exception;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    CustomResponseStatus responseStatus;
}
