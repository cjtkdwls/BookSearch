package com.thomas.booksearch.user.service;

import com.thomas.booksearch.security.config.TokenProvider;
import com.thomas.booksearch.user.exception.AuthException;
import com.thomas.booksearch.user.model.AuthResponse;
import com.thomas.booksearch.user.model.User;
import com.thomas.booksearch.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByToken(HttpServletRequest req) {
        return userRepository.findByUserId(tokenProvider.getUserId(tokenProvider.getJwtToken(req)))
                .orElseThrow(()-> new AuthException("인증정보가 올바르지 않습니다"));
    }

    public AuthResponse register(String userId, String userPassword) {
        User user = User.builder()
                .userId(userId)
                .userPassword(passwordEncoder.encode(userPassword))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        if(userRepository.findByUserId(userId).isPresent()){
            throw new AuthException("이미 존재하는 계정명입니다");
        } else {
            userRepository.save(user);
            return AuthResponse.builder()
                                .message("회원가입을 완료했습니다")
                                .build();
        }
    }

    public AuthResponse login(String userId, String userPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthException("존재하지 않는 사용자입니다"));
        if(!passwordEncoder.matches(userPassword, user.getPassword())) {
            return AuthResponse.builder()
                                .message("비밀번호를 확인해주세요")
                                .build();
        }else{
            return AuthResponse.builder()
                                .message("로그인에 성공했습니다")
                                .jwtToken(tokenProvider.createJwtToken(user.getUserId(), user.getRoles()))
                                .build();
        }
    }
}
