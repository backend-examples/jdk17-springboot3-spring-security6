package com.example.service;

import com.example.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(UserModel userModel);

    Map<String, Object> registerUser(UserModel userModel);
}
