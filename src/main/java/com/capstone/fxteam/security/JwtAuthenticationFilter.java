package com.capstone.fxteam.security;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.capstone.fxteam.constant.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String token = jwtUtils.resolveToken(request.getHeader("Authorization"));
        log.info("token : " + token);

        if (token != null && !token.isEmpty()) {
            try {
                jwtUtils.parseToken(token);
                if (!request.getRequestURI().equals("/api/reissue")) {
                    String isLogout = redisUtils.getData(token);
                    if (isLogout == null) {
                        log.info("enter the this logic");
                        Authentication authentication = jwtUtils.getAuthentication(token);
                        log.info("filter get Authorities() : " + authentication.getAuthorities().toString());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                log.info("expired Token");
                request.setAttribute("exception", CustomResponseStatus.EXPIRED_JWT.getMessage());
            } catch (JwtException e) {
                log.info("invalid token");
                request.setAttribute("exception", CustomResponseStatus.BAD_JWT.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
