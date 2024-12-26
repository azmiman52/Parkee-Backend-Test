package com.parkee.library.domains.models;

import lombok.Data;

@Data
public class CreateBookDto {
    private String title;
    private String isbn;
    private Integer stock;
}
