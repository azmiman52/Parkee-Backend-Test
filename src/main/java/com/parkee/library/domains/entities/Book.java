package com.parkee.library.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "title", unique = true)
    @Comment(value = "Title of The Book", on = "title")
    private String title;

    @Column(name = "isbn", unique = true)
    @Comment(value = "ISBN", on = "isbn")
    private String isbn;

    @Column(name = "stock")
    @Comment(value = "Stock of The Book", on = "stock")
    private Integer stock;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<RentHistory> rentHistories;
}