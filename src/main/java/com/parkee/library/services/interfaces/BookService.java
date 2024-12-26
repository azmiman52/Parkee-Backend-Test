package com.parkee.library.services.interfaces;

import com.parkee.library.domains.entities.Book;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateBookDto;
import com.parkee.library.domains.models.SimplePage;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BaseResponse<Book> createBook(CreateBookDto payload);

    BaseResponse<SimplePage<Book>> findAll(Pageable pageable);
}
