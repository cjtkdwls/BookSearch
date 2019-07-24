package com.thomas.booksearch.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class KakaoBookResponse {
	private Meta meta;
	private List<Documents> documents;

	public List<Book> convertBookList() {
		return documents.stream()
						.map(Documents::toBook)
						.collect(Collectors.toList());
	}

	@Data
	public static class Meta {
		@JsonProperty("total_count")
		private int totalCount;
		@JsonProperty("pageable_count")
		private int pageableCount;
		@JsonProperty("is_end")
		private boolean isEnd;
	}

	@Data
	public static class Documents implements Bookable {
		private String title;
		private String contents;
		private String url;
		private String isbn;
		private String datetime;
		private List<String> authors;
		private String publisher;
		private List<String> translators;
		private int price;
		@JsonProperty("sale_price")
		private int salePrice;
		private String thumbnail;
		private String status;

		@Override
		public Book toBook() {
			return Book.builder()
					   .title(title)
					   .thumbnail(thumbnail)
					   .contents(contents)
					   .isbn(isbn)
					   .authors(authors)
					   .publisher(publisher)
					   .datetime(datetime)
					   .price(price)
					   .salePrice(salePrice)
					   .url(url)
					   .translators(translators)
					   .status(status)
					   .source("Kakao")
					   .build();
		}
	}
}
