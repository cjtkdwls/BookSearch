package com.thomas.booksearch.search.service;

import com.thomas.booksearch.search.model.SearchResult;
import com.thomas.booksearch.search.repo.BookConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookSearchService {
	private List<BookConnector> bookConnectorList;

	@Autowired
	public void setBookConnectorList(List<BookConnector> bookConnectorList) {
		this.bookConnectorList = bookConnectorList.stream()
				.sorted(Comparator.comparingInt(BookConnector::getPriority))
				.collect(Collectors.toList());
	}

	public SearchResult retrieve(String query, int page, int size) {
		for (BookConnector bookConnector : bookConnectorList) {
			try {
				return bookConnector.retrieveBookList(query, page, size);
			} catch (Exception e) {
				log.error("BookConnector 실패 : {}", bookConnector.getClass().getSimpleName());
			}
		}
		return null;
	}
}
