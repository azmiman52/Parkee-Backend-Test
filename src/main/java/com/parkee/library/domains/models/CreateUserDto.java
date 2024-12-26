package com.parkee.library.domains.models;

import lombok.Data;

@Data
public class CreateUserDto {
    private String nik;
    private String name;
    private String email;
}
