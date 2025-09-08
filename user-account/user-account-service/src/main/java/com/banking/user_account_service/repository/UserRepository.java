package com.banking.user_account_service.repository;

import com.banking.user_account_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Keerthana
 **/
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}

