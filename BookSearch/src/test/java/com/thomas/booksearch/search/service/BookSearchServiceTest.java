package com.thomas.booksearch.search.service;

import com.thomas.booksearch.search.model.SearchResult;
import com.thomas.booksearch.search.repo.KakaoBookConnector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchServiceTest {
    @Autowired
    private BookSearchService bookSearchService;

    @MockBean
    private KakaoBookConnector kakaoBookConnector;

    @Test
    public void kakaoConnectorFailedTest() {
        doThrow(new RuntimeException()).when(kakaoBookConnector).retrieveBookList(any(), anyInt(), anyInt());
        SearchResult result = bookSearchService.retrieve("미움 받을 용기", 1, 10);

        result.getBookList().forEach((book -> assertEquals(book.getSource(), "Naver")));
    }
}
