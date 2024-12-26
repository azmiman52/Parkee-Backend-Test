package com.parkee.library.domains.models;

import lombok.Data;

import java.util.List;

@Data
public class SimplePage<T> {
    private List<T> content;
    private Integer totalPages;
    private Integer totalElements;
    private Integer size;
    private Integer number;
}
