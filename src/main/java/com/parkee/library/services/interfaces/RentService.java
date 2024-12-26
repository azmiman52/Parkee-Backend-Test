package com.parkee.library.services.interfaces;

import com.parkee.library.domains.entities.RentHistory;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateRentDto;
import com.parkee.library.domains.models.ReturnBookDto;
import com.parkee.library.domains.models.SimplePage;
import org.springframework.data.domain.Pageable;

public interface RentService {
    BaseResponse<RentHistory> createRent(CreateRentDto payload);

    BaseResponse<RentHistory> returnBook(ReturnBookDto payload);

    BaseResponse<SimplePage<RentHistory>> findRentHistByUserId(Integer userId, Pageable pageable);
}
