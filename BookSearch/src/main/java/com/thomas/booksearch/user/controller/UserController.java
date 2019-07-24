package com.thomas.booksearch.user.controller;

import com.thomas.booksearch.security.config.TokenProvider;
import com.thomas.booksearch.user.model.User;
import com.thomas.booksearch.user.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@Api(value = "UserController", description = "사용자 기능 관련 API controller")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenProvider tokenProvider;

    @GetMapping(value = "/user")
    @ApiOperation(value = "User 정보조회", notes = "User 정보 반환")
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "사용자 인증을 위한 JWT Token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<?> userInfo(HttpServletRequest req) {
        return ResponseEntity.ok(User.builder().userId(tokenProvider.getUserId(tokenProvider.getJwtToken(req))).build());
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "User 로그인", notes = "User 로그인, 토큰 반환")
    public ResponseEntity<?> userLogin(@RequestParam("userId") String userId,
                                          @RequestParam("userPassword") String userPassword) {
        log.info("{} login 시도 : {}", userId, this.getClass().getSimpleName());
        return ResponseEntity.ok(userService.login(userId,userPassword));
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "User 회원가입", notes = "User 신규등록")
    public ResponseEntity<?> userRegister(@RequestParam("userId") String userId,
                                             @RequestParam("userPassword") String userPassword) {
        log.info("{} register 시도 : {}", userId, this.getClass().getSimpleName());
        return ResponseEntity.ok(userService.register(userId, userPassword));
    }
}
