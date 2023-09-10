package com.example.handler;

import com.example.constant.MessagesConstant;
import com.example.enums.CodeEnum;
import com.example.utils.ResponseResult;
import com.example.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(CodeEnum.NOT_AUTHENTICATION.getCode());
        ResponseUtils.result(response, ResponseResult.builder().code(CodeEnum.NOT_AUTHENTICATION.getCode()).message(MessagesConstant.NOT_AUTHENTICATION).build());
    }
}
