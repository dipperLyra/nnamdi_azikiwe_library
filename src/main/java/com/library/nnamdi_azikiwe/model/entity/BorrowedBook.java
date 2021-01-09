package com.library.nnamdi_azikiwe.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BorrowedBook {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany private List<Book> books;
    @ManyToOne private User user;
    @CreationTimestamp private Timestamp borrowedAt;

    public BorrowedBook(List<Book> books, User user, Timestamp borrowedAt) {
        this.books = books;
        this.user = user;
        this.borrowedAt = borrowedAt;
    }
}
