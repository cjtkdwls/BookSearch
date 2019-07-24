package com.thomas.booksearch.search.repo;

import com.thomas.booksearch.search.model.History;
import com.thomas.booksearch.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);

    Optional<History> findByKeywordName(String keyword);
}
