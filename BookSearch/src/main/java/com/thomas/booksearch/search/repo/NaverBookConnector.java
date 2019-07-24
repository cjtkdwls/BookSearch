package com.thomas.booksearch.search.repo;

import com.thomas.booksearch.search.model.NaverBookResponse;
import com.thomas.booksearch.search.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Repository
public class NaverBookConnector implements BookConnector {
	@Value("${book.api.naver}")
	private String path;
	@Value("${book.api.naver.clientId}")
	private String clientId;
	@Value("${book.api.naver.secretKey}")
	private String key;
	private RestTemplate restTemplate;

	@Autowired
	public NaverBookConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public SearchResult retrieveBookList(String query, int page, int size) {
		URI uri = UriComponentsBuilder.fromUriString(path)
				.queryParam("query", query)
				.queryParam("start", page)
				.queryParam("display", size)
				.encode()
				.build()
				.toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", key);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<NaverBookResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverBookResponse.class);
		NaverBookResponse naverBookResponse = responseEntity.getBody();

		return new SearchResult(responseEntity.getBody().getTotal(), naverBookResponse.convertBookList());
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
