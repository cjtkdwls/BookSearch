package com.thomas.booksearch.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private long validDuration = 1000 * (60 * 60) * 24 * 7; // 토큰 유효기간 7일

    @Value("spring.jwt.secret")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void initSecretKey() {
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }

    /**
     * 사용자 Authenticating 을 위한 JWT 토큰 생성
     */
    public String createJwtToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + validDuration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     *  JWT Token을 파싱하여 사용자 정보 get
     */
    public String getUserId(String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }

    /**
     *  JWT Token을 파싱하여 Authentication 정보 가져오기
     */
    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     *  Request Header에서 Token 가져오기
     */
    public String getJwtToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    /**
     *  Token의 유효기간이 만료되었는지 확인
     */
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
