package com.thomas.booksearch.search.model;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 상세 정보에는 제목, 도서 썸네일, 소개, ISBN, 저자, 출판사, 출판일, 정가, 판매가가 포함되어야
 **/
@Data
@Builder
public class Book {
	private String title;
	private String thumbnail;
	private String contents;
	private String isbn;
	private List<String> authors;
	private String publisher;
	private String datetime;
	private int price;
	private int salePrice;

	private String url;
	private List<String> translators;
	private String status;

	//Kakao, Naver
	//@JsonIgnore	//front에서 출처를 모르게 한다는 의미 해석 모호
	private String source;
}
