package com.capstone.fxteam.security;

import com.capstone.fxteam.member.repository.MemberRepository;
import com.capstone.fxteam.member.service.PrincipalDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public final class JwtUtils {
    private final MemberRepository memberRepository;
    private final PrincipalDetailsServiceImpl userDetailsService;
    private final String SECRET_KEY = "saldkfjlaksfjlitulkasjgklasghisaouytlasjktkalthlkjas";

    public static final long TOKEN_VALID_TIME = 1000L * 60 * 5 * 12; // 1시간
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 144;
    public static final long REFRESH_TOKEN_VALID_TIME_IN_REDIS = 60 * 60 * 24 * 7;

    public Key getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String getEmailInToken(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailInToken(token));
        log.info("userDetails.getUsername : " + userDetails.getUsername());
        log.info("userDetails.getPassword : " + userDetails.getPassword());
        log.info("getAuthorities() : " + userDetails.getAuthorities().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(String token) {
        if (token != null) {
            return token.substring("Bearer ".length());
        } else {
            return "";
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(token).getBody();
    }

    public Long getExpiration(String jwtToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
                .parseClaimsJws(jwtToken).getBody().getExpiration();
        long now = System.currentTimeMillis();
        return expiration.getTime() - now;
    }
}
