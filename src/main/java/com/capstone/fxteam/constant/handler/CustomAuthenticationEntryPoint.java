package com.capstone.fxteam.constant.handler;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        log.error("exception : " + exception);

        /**
         * 토큰 없는 경우
         */
        if (exception == null) {
            log.info("[NULL TOKEN]");
            response.sendRedirect("/exception/entrypoint/nullToken");
        }

        /**
         * 토큰 만료된 경우
         */
        if (Objects.equals(exception, CustomResponseStatus.EXPIRED_JWT.getMessage())) {
            response.sendRedirect("/exception/entrypoint/expiredToken");
        }

        /**
         * 토큰 시그니처가 다른 경우
         */
        if (Objects.equals(exception, CustomResponseStatus.BAD_JWT.getMessage())) {
            log.info("[BAD TOKEN]");
            response.sendRedirect("/exception/entrypoint/badToken");

        }
    }
}
