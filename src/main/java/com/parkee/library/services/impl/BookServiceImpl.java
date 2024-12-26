package com.parkee.library.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkee.library.domains.entities.Book;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateBookDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.repositories.BookRepository;
import com.parkee.library.services.interfaces.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ObjectMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public BaseResponse<Book> createBook(CreateBookDto payload) {
        Book book = new Book();

        try {
            book.setTitle(payload.getTitle());
            book.setIsbn(payload.getIsbn());
            book.setStock(payload.getStock());

            long count = bookRepository.countByTitleIgnoreCaseOrIsbnIgnoreCase(payload.getTitle(), payload.getIsbn());
            if (count > 0) {
                var res = BaseResponse.setFailed(book, 409);
                res.setMessage("Title or ISBN is already used");
                return res;
            }

            bookRepository.save(book);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return BaseResponse.setFailed(book, 400);
        }
        return BaseResponse.setSuccess(book);
    }

    @Override
    public BaseResponse<SimplePage<Book>> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        return BaseResponse.setSuccess(mapper.convertValue(books, new TypeReference<SimplePage<Book>>() {
        }));
    }
}
