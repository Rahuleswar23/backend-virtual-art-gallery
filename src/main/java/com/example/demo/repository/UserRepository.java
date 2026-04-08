package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // 🔥 Custom method (VERY IMPORTANT)
    User findByEmail(String email);
}