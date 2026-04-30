package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ REGISTER USER (FIXED)
    @PostMapping
    public User registerUser(@RequestBody User user) {

        System.out.println("REGISTER REQUEST: " + user.getEmail());

        // 🔥 VALIDATION
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("Email required");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password required");
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("visitor");
        }

        // 🔥 NORMALIZE ROLE
        user.setRole(user.getRole().toLowerCase());

        return userRepository.save(user);
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}