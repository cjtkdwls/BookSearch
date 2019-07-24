package com.thomas.booksearch.search.repo;

import com.thomas.booksearch.search.model.KakaoBookResponse;
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
public class KakaoBookConnector implements BookConnector {
	@Value("${book.api.kakao}")
	private String path;
	@Value("${book.api.kakao.authorization}")
	private String key;
	private RestTemplate restTemplate;

	@Autowired
	public KakaoBookConnector(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public SearchResult retrieveBookList(String query, int page, int size) {
		URI uri = UriComponentsBuilder.fromUriString(path)
				.queryParam("query", query)
				.queryParam("page", page)
				.queryParam("size", size)
				.encode()
				.build()
				.toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", key);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<KakaoBookResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoBookResponse.class);
		KakaoBookResponse kakaoBookResponse = responseEntity.getBody();

		return new SearchResult(kakaoBookResponse.getMeta().getPageableCount(), kakaoBookResponse.convertBookList());
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
