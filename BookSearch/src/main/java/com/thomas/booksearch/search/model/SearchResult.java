package com.thomas.booksearch.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SearchResult {
    private int totalCount;
    List<Book> bookList;
}
