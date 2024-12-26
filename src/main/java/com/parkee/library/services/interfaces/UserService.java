package com.parkee.library.services.interfaces;

import com.parkee.library.domains.entities.User;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateUserDto;
import com.parkee.library.domains.models.SimplePage;
import org.springframework.data.domain.Pageable;

public interface UserService {
    BaseResponse<User> createUser(CreateUserDto payload);

    BaseResponse<SimplePage<User>> findAll(Pageable pageable);

}
