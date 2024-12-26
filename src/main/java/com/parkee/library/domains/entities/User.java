package com.parkee.library.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "nik", unique = true)
    @Comment(value = "User's NIK", on = "nik")
    private String nik;

    @Column(name = "name")
    @Comment(value = "Name", on = "name")
    private String name;

    @Column(name = "email")
    @Comment(value = "User's email", on = "email")
    private String email;

    @Column(name = "can_borrow", columnDefinition = "Default true")
    @Comment(value = "Check if the user can borrow a book or not", on = "can_borrow")
    private Boolean canBorrow = Boolean.TRUE;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<RentHistory> rentHistories;
}