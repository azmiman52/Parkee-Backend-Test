package com.parkee.library.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkee.library.domains.entities.User;
import com.parkee.library.domains.models.BaseResponse;
import com.parkee.library.domains.models.CreateUserDto;
import com.parkee.library.domains.models.SimplePage;
import com.parkee.library.repositories.UserRepository;
import com.parkee.library.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public BaseResponse<User> createUser(CreateUserDto payload) {
        User user = new User();

        try {
            user.setNik(payload.getNik());
            user.setName(payload.getName());
            user.setEmail(payload.getEmail());

            long count = userRepository.countByNikIgnoreCaseOrEmailIgnoreCase(payload.getNik(), payload.getEmail());
            if (count > 0) {
                var res = BaseResponse.setFailed(user, 409);
                res.setMessage("NIK or email is already used");
                return res;
            }

            userRepository.save(user);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return BaseResponse.setFailed(user, 400);
        }
        return BaseResponse.setSuccess(user);
    }

    @Override
    public BaseResponse<SimplePage<User>> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return BaseResponse.setSuccess(mapper.convertValue(users, new TypeReference<SimplePage<User>>() {
        }));
    }
}
