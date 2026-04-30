package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        Map<String, Object> res = new HashMap<>();

        // 🔍 find user
        User existing = userRepository.findByEmail(user.getEmail().trim());

        if (existing == null) {
            res.put("message", "User not found");
            return res;
        }

        // 🔥 SAFE PASSWORD CHECK
        String dbPass = existing.getPassword() == null ? "" : existing.getPassword().trim();
        String inputPass = user.getPassword() == null ? "" : user.getPassword().trim();

        // 🔥 DEBUG (VERY IMPORTANT)
        System.out.println("DB PASS = [" + dbPass + "]");
        System.out.println("INPUT PASS = [" + inputPass + "]");

        if (!dbPass.equals(inputPass)) {
            res.put("message", "Invalid password");
            return res;
        }

        // 🔥 ROLE CHECK
        if (!existing.getRole().equalsIgnoreCase(user.getRole())) {
            res.put("message", "Wrong role selected");
            return res;
        }

        // ✅ SUCCESS
        res.put("message", "Login successful");
        res.put("role", existing.getRole());
        res.put("email", existing.getEmail());

        return res;
    }

    // ✅ SIGNUP
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        user.setEmail(user.getEmail().trim());
        user.setPassword(user.getPassword().trim());
        user.setRole(user.getRole().toLowerCase());
        return userRepository.save(user);
    }
}