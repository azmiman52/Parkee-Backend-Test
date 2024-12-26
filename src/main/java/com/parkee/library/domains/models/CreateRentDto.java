package com.parkee.library.domains.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRentDto {
    private Integer userId;
    private String isbn;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate rentDue;
}
