package com.thomas.booksearch.search.repo;

import com.thomas.booksearch.BookSearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookSearchApplication.class)
public class KakaoBookConnectorTest {
	@Autowired
	private KakaoBookConnector kakaoBookConnector;

	@Test
	public void test() {
		System.out.println(kakaoBookConnector.retrieveBookList("미움받을 용기", 1, 10));
	}
}
