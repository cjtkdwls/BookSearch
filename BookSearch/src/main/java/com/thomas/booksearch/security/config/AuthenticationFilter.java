package com.thomas.booksearch.security.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    private TokenProvider tokenProvider;

    /**
     *  request에서 들어오는 정보에서 Token을 파싱해 유효성 검사 실행하는 필터 추가
     */
    @Override
    public void doFilter(ServletRequest servletRequest
                        ,ServletResponse servletResponse
                        ,FilterChain filterChain) throws IOException, ServletException {

        String token = tokenProvider.getJwtToken((HttpServletRequest) servletRequest);
        if (token != null && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
