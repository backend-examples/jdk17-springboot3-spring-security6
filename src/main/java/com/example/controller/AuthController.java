package com.example.controller;

import com.example.enums.CodeEnum;
import com.example.model.UserModel;
import com.example.service.AuthService;
import com.example.utils.ResponseResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserModel userModel) {

        Map<String, Object> token = authService.login(userModel);

        return ResponseResult.builder().code(CodeEnum.SUCCESS.getCode()).message(CodeEnum.SUCCESS.getMessage()).data(token).build();
    }

    @PostMapping("/register")
    public ResponseResult registerUser(@Valid @RequestBody UserModel userModel) {

        Map<String, Object> resultMap = authService.registerUser(userModel);

        return ResponseResult.builder().ok(resultMap).build();
    }
}
