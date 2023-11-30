package com.capstone.fxteam.security;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
        String token = jwtUtils.resolveToken(request.getHeader("Authorization"));
        log.info("Enter Token INFO : " + token);

        if (!token.isEmpty()) {
            try {
                jwtUtils.parseToken(token);
                if (!request.getRequestURI().equals("/api/v1/reissue")) {
                    String isLogout = redisUtils.getData(token);
                    // getData 해서 값이 가져와지면 AT가 블랙리스트에 등록된 상태이므로 로그아웃된 상태임.
                    if (isLogout == null) {
                        Authentication authentication = jwtUtils.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                log.error("Enter [EXPIRED TOKEN]");
                request.setAttribute("exception", CustomResponseStatus.EXPIRED_JWT.getMessage());
            } catch (JwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                     IllegalArgumentException e) {
                log.error("Enter [INVALID TOKEN]");
                request.setAttribute("exception", CustomResponseStatus.BAD_JWT.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
