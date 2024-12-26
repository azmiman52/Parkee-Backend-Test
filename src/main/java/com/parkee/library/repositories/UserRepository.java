package com.parkee.library.repositories;

import com.parkee.library.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    long countByNikIgnoreCaseOrEmailIgnoreCase(String nik, String email);
}
