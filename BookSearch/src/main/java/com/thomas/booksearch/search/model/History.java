package com.thomas.booksearch.search.model;

import com.thomas.booksearch.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historySeq;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userSeq")
    private User user;

    @Column(nullable = false, unique = true)
    private String keywordName;

    @Column(nullable = false)
    private Date searchDate;
}
