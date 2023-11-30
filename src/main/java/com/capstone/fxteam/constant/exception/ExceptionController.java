package com.capstone.fxteam.constant.exception;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
@Slf4j
public class ExceptionController {
    @GetMapping("/entrypoint")
    public void entrypointException() {
        log.info("entryPoint Controller entered");
        throw new CustomException(CustomResponseStatus.AUTHENTICATION_FAILED);
    }

    @GetMapping("/entrypoint/nullToken")
    public void nullTokenException() {
        log.info("nullTokenException Controller entered");
        throw new CustomException(CustomResponseStatus.NULL_TOKEN);
    }

    @GetMapping("/entrypoint/expiredToken")
    public void expiredTokenException() {
        log.error("expiredTokenException Controller entered");
        throw new CustomException(CustomResponseStatus.EXPIRED_JWT);
    }

    @GetMapping("/entrypoint/badToken")
    public void badTokenException() {
        log.info("badTokenException Controller entered");
        throw new CustomException(CustomResponseStatus.AUTHENTICATION_FAILED);
    }

    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        log.info("accessDeniedException Controller entered");
        throw new CustomException(CustomResponseStatus.AUTHORIZATION_FAILED);
    }
}
