package com.project.app.securitys.repositorieSecurity;

import com.project.app.securitys.modelSecurity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}

