package com.parkee.library.controllers;

import com.parkee.library.domains.entities.User;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateUserDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    @Operation(summary = "Create a user")
    public ResponseEntity<BaseResponse<User>> createUser(@RequestBody CreateUserDto payload){
        BaseResponse<User> response = userService.createUser(payload);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @GetMapping("")
    @Operation(summary = "Get a list of users")
    public ResponseEntity<BaseResponse<SimplePage<User>>> getUsers(@ParameterObject Pageable pageable) {
        var response = userService.findAll(pageable);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}
