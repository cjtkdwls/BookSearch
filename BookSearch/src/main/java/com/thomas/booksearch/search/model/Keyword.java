package com.thomas.booksearch.search.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="keyword")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long keywordSeq;

    @Column(nullable = false, unique = true)
    private String keywordName;

    @Column(nullable = false)
    private int keywordCount;
}
