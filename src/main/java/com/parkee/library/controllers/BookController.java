package com.parkee.library.controllers;

import com.parkee.library.domains.entities.Book;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateBookDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.services.interfaces.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    @Operation(summary = "Create a book")
    public ResponseEntity<BaseResponse<Book>> createBook(@RequestBody CreateBookDto payload) {
        var response = bookService.createBook(payload);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("")
    @Operation(summary = "Get a list of books")
    public ResponseEntity<BaseResponse<SimplePage<Book>>> getBooks(@ParameterObject Pageable pageable) {
        var response = bookService.findAll(pageable);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}
