package com.thomas.booksearch.user;

import com.thomas.booksearch.security.config.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    TokenProvider tokenProvider;

    @Test
    public void tokenGenerateTest() {
        log.info("Generated Key : {}", tokenProvider.createJwtToken("knight0202", Collections.singletonList("ROLE_USER")));
    }
}
