package com.thomas.booksearch.search.controller;

import com.thomas.booksearch.search.model.History;
import com.thomas.booksearch.search.model.Keyword;
import com.thomas.booksearch.search.model.SearchResult;
import com.thomas.booksearch.search.service.BookSearchService;
import com.thomas.booksearch.search.service.SearchKeywordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/book")
@Api(value = "BookSearchController", description = "책 검색 관련 API controller")
public class BookSearchController {

    @Autowired
    BookSearchService bookSearchService;
    @Autowired
    SearchKeywordService searchKeywordService;

    // 책 검색
    @GetMapping(value = "/search")
    @ApiOperation(value = "책 검색", notes = "페이지와, 사이즈 없을 시 기본 검색으로 1 page, 20개 검색")
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "사용자 인증을 위한 JWT Token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<?> search(ServletRequest servletRequest,
                                    @RequestParam(value = "query") String query,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        // 페이지 입력없이 검색어로만 찾았을 때 처리
        if(page == 0 && size == 0) {
            page = 1;
            size = 10;
            // 검색기록 추가
            searchKeywordService.saveKeywordHistory((HttpServletRequest) servletRequest, query);
        }
        SearchResult result = bookSearchService.retrieve(query, page, size);
        return ResponseEntity.ok(result);
    }

    // 책 검색 히스토리
    @GetMapping(value = "/history")
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "사용자 인증을 위한 JWT Token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<?> history(ServletRequest servletRequest) {
        List<History> histories = searchKeywordService.getPersonalHistory((HttpServletRequest) servletRequest);
        return ResponseEntity.ok(histories);
    }

    // 책 검색 키워드
    @GetMapping(value = "/keyword")
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "사용자 인증을 위한 JWT Token", required = true, dataType = "String", paramType = "header")})
    public ResponseEntity<?> keyword() {
        List<Keyword> keywords = searchKeywordService.getBestTenKeyword();
        return ResponseEntity.ok(keywords);
    }
}
