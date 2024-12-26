package com.parkee.library.repositories;

import com.parkee.library.domains.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    long countByTitleIgnoreCaseOrIsbnIgnoreCase(String title, String isbn);
    Optional<Book> findFirstByIsbn(String isbn);
}