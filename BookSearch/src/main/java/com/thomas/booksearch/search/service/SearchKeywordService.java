package com.thomas.booksearch.search.service;

import com.thomas.booksearch.search.model.History;
import com.thomas.booksearch.search.model.Keyword;
import com.thomas.booksearch.search.repo.HistoryRepository;
import com.thomas.booksearch.search.repo.KeywordRepository;
import com.thomas.booksearch.user.model.User;
import com.thomas.booksearch.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchKeywordService {
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    KeywordRepository keywordRepository;
    @Autowired
    UserService userService;

    // 개인 히스토리 및 전체 키워드 카운트 증가
    synchronized public History saveKeywordHistory(HttpServletRequest req, String keyword) {
        User master = userService.getUserByToken(req);
        List<History> history = historyRepository.findByUser(master)
                .stream()
                .filter(historyItem -> historyItem.getKeywordName().equals(keyword))
                .collect(Collectors.toList());

        if(keywordRepository.findByKeywordName(keyword).isPresent()){
            Keyword oldKeyword = keywordRepository.findByKeywordName(keyword).get();
            keywordRepository.save(Keyword.builder().keywordSeq(oldKeyword.getKeywordSeq())
                                                    .keywordName(oldKeyword.getKeywordName())
                                                    .keywordCount(oldKeyword.getKeywordCount() + 1)
                                                    .build());
        } else {
            keywordRepository.save(keywordRepository.findByKeywordName(keyword).orElse(Keyword.builder().keywordName(keyword).keywordCount(1).build()));
        }
        if(history.size() > 0) {
            history.get(0).setSearchDate(new Date());
            return historyRepository.save(history.get(0));
        }else{
            return historyRepository.save(History.builder()
                    .keywordName(keyword)
                    .user(master)
                    .searchDate(new Date())
                    .build());
        }
    }

    // 개인 히스토리 조회 (최신순)
    public List<History> getPersonalHistory(HttpServletRequest req) {
        List<History> histories = historyRepository.findByUser(userService.getUserByToken(req));
        Collections.sort(histories, (History o1, History o2)-> {
            if(o1.getSearchDate().before(o2.getSearchDate())) {
                return 1;
            } else if(o1.getSearchDate().after(o2.getSearchDate())) {
                return -1;
            } else {
                return 0;
            }
        });
        return histories;
    }

    // 전체 베스트 키워드 (조회순)
    public List<Keyword> getBestTenKeyword() {
        Pageable bestTen = PageRequest.of(0, 10, Sort.by("keywordCount").descending());
        return keywordRepository.findAll(bestTen).getContent();
    }
}
