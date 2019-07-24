package com.thomas.booksearch.search.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class NaverBookResponse {
	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private List<Item> items;

	public List<Book> convertBookList() {
		return items.stream()
					.map(Item::toBook)
					.collect(Collectors.toList());
	}

	@Data
	public static class Item implements Bookable {
		private static final String AUTHOR_DELIMITER = "\\|";
		private String title;
		private String link;
		private String image;
		private String author;
		private String price;
		private String discount;
		private String publisher;
		private String pubDate;
		private String isbn;
		private String description;

		@Override
		public Book toBook() {
			return Book.builder()
					.title(title)
					.thumbnail(image)
					.contents(description)
					.isbn(isbn)
					.authors(toAuthorList())
					.publisher(publisher)
					.datetime(pubDate)
					.price(convertIntegerAble(price).orElse(0))
					.salePrice(convertIntegerAble(discount).orElseGet(() -> convertIntegerAble(price).orElse(0)))
					.url(link)
					.source("Naver")
					.build();
		}

		private Optional<Integer> convertIntegerAble(String target) {
			return Optional.ofNullable(target)
					.filter(NonNullTarget -> !target.isEmpty())
					.map(Integer::valueOf);
		}

		private List<String> toAuthorList() {
			return Optional.ofNullable(author)
					.map(concatenatedAuthor -> concatenatedAuthor.split(AUTHOR_DELIMITER))
					.map(Arrays::asList)
					.orElse(null);
		}
	}
}
