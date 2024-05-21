package com.example.miniaspireapi.repository;

import com.example.miniaspireapi.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author devenderchaudhary
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
