package com.example.handler;

import com.example.enums.CodeEnum;
import com.example.utils.ResponseResult;
import com.example.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        ResponseUtils.result(httpServletResponse, ResponseResult.builder().code(CodeEnum.FORBIDDEN.getCode()).message(CodeEnum.FORBIDDEN.getMessage()).build());
    }
}
