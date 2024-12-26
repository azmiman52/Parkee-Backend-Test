package com.parkee.library.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkee.library.constants.ReturnStatus;
import com.parkee.library.domains.entities.Book;
import com.parkee.library.domains.entities.RentHistory;
import com.parkee.library.domains.entities.User;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateRentDto;
import com.parkee.library.domains.models.ReturnBookDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.repositories.BookRepository;
import com.parkee.library.repositories.RentHistoryRepository;
import com.parkee.library.repositories.UserRepository;
import com.parkee.library.services.interfaces.RentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RentServiceImpl implements RentService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RentHistoryRepository rentHistRepository;
    private final ObjectMapper mapper;

    @Value("#{'${rent.max.days:30}'}")
    private int maxRentDay;

    public RentServiceImpl(UserRepository userRepository, BookRepository bookRepository, RentHistoryRepository rentHistRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.rentHistRepository = rentHistRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Modifying
    @Override
    public BaseResponse<RentHistory> createRent(CreateRentDto payload) {
        var response = BaseResponse.<RentHistory>setSuccess(null);

        var userOptional = userRepository.findById(payload.getUserId());
        if (userOptional.isEmpty()) {
            response.setMessage(String.format("User with id: %d does not exist", payload.getUserId()));
            response.setCode(400);
            return response;
        }

        var bookOptional = bookRepository.findFirstByIsbn(payload.getIsbn());
        if (bookOptional.isEmpty()) {
            response.setMessage(String.format("Book with isbn: %s does not exist", payload.getIsbn()));
            response.setCode(400);
            return response;
        }
        Book book = bookOptional.get();
        User user = userOptional.get();

        if (Boolean.FALSE.equals(userOptional.get().getCanBorrow())) {
            response.setMessage("User already borrow a book");
            response.setCode(400);
            return response;
        }

        if (book.getStock() <= 0) {
            response.setMessage("Book is out of stock");
            response.setCode(400);
            return response;
        }

        long rentDuration = ChronoUnit.DAYS.between(LocalDate.now(), payload.getRentDue());
        if (rentDuration <= 0) {
            response.setMessage("Rent minimum period is 1 day");
            response.setCode(400);
            return response;
        }

        if (rentDuration > maxRentDay) {
            response.setMessage(String.format("Rent period exceeds the limit: %d", maxRentDay));
            response.setCode(400);
            return response;
        }

        user.setCanBorrow(false);
        book.setStock(book.getStock() - 1);

        userRepository.save(user);
        bookRepository.save(book);
        RentHistory rentHistory = new RentHistory();
        rentHistory.setRentDate(LocalDate.now());
        rentHistory.setRentDue(payload.getRentDue());
        rentHistory.setBook(book);
        rentHistory.setUser(user);
        rentHistory.setStatus(ReturnStatus.BORROWED);
        rentHistRepository.save(rentHistory);
        response.setData(rentHistory);
        return response;
    }

    @Transactional
    @Override
    public BaseResponse<RentHistory> returnBook(ReturnBookDto payload) {
        var response = BaseResponse.<RentHistory>setSuccess(null);

        var rentHistOpt = rentHistRepository.findOneRentHistory(payload.getUserId(), payload.getIsbn());

        if (rentHistOpt.isEmpty()) {
            response.setMessage("Rent not found");
            response.setCode(400);
            return response;
        }
        RentHistory rentHist = rentHistOpt.get();
        var rentStatus = LocalDate.now().isAfter(rentHist.getRentDue()) ? ReturnStatus.OVERDUE : ReturnStatus.ON_TIME;
        rentHist.setStatus(rentStatus);
        rentHist.setReturnDate(LocalDate.now());
        rentHistRepository.save(rentHist);

        var book = rentHist.getBook();
        var user = rentHist.getUser();
        book.setStock(book.getStock() + 1);
        user.setCanBorrow(Boolean.TRUE);
        userRepository.save(user);
        bookRepository.save(book);

        response.setData(rentHist);
        return response;
    }

    @Override
    public BaseResponse<SimplePage<RentHistory>> findRentHistByUserId(Integer userId, Pageable pageable) {
        Page<RentHistory> rentHistories = rentHistRepository.findAllByUserId(userId, pageable);

        return BaseResponse.setSuccess(mapper.convertValue(rentHistories, new TypeReference<SimplePage<RentHistory>>() {
        }));
    }

}
