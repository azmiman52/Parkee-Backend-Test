package com.parkee.library.controllers;

import com.parkee.library.domains.entities.RentHistory;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateRentDto;
import com.parkee.library.domains.models.ReturnBookDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.services.interfaces.RentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("")
    @Operation(summary = "Create rent for a book for a specific user")
    public ResponseEntity<BaseResponse<RentHistory>> createRent(@RequestBody CreateRentDto payload){
        BaseResponse<RentHistory> response = rentService.createRent(payload);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @PostMapping("/return-book")
    @Operation(summary = "User return book that borrowed")
    public ResponseEntity<BaseResponse<RentHistory>> returnBook(@RequestBody ReturnBookDto payload){
        BaseResponse<RentHistory> response = rentService.returnBook(payload);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("/user/{userId}/histories")
    @Operation(summary = "Get rent histories of a specific user")
    public ResponseEntity<BaseResponse<SimplePage<RentHistory>>> getUserRentHistories(@PathVariable Integer userId, @ParameterObject Pageable pageable){
        var response = rentService.findRentHistByUserId(userId, pageable);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}
