package com.parkee.library.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkee.library.constants.ReturnStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "rent_history")
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Book book;

    @Column(name = "rent_date")
    @Comment(value = "Date when the rent started", on = "rent_date")
    private LocalDate rentDate;

    @Column(name = "rent_due")
    @Comment(value = "Due day of the rent", on = "rent_due")
    private LocalDate rentDue;

    @Column(name = "return_date")
    @Comment(value = "Return date of the book", on = "return_date")
    private LocalDate returnDate;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    @Comment(value = "Enum of ReturnStatus", on = "status")
    private ReturnStatus status;
}
