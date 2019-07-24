package com.thomas.booksearch.search.repo;

import com.thomas.booksearch.search.model.SearchResult;

public interface BookConnector {
	int getPriority();

	SearchResult retrieveBookList(String query, int page, int size);
}
