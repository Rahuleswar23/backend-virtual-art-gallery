package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    // ✅ ADD THIS (IMPORTANT)
    @Autowired
    private UserRepository userRepository;

    // ================= SIGNUP =================
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing != null) {
            res.put("message", "User already exists");
            return res;
        }

        user.setRole(user.getRole().toLowerCase()); // normalize
        userRepository.save(user);

        res.put("message", "Signup successful");
        return res;
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing == null) {
            res.put("message", "User not found");
            return res;
        }

        if (!existing.getPassword().equals(user.getPassword())) {
            res.put("message", "Invalid password");
            return res;
        }

        if (!existing.getRole().equalsIgnoreCase(user.getRole())) {
            res.put("message", "Wrong role selected");
            return res;
        }

        // ✅ SUCCESS
        res.put("message", "Login successful");
        res.put("role", existing.getRole());

        return res;
    }
}