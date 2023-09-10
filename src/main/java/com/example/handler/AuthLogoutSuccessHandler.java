package com.example.handler;


import com.example.model.SecurityUser;
import com.example.service.TokenService;
import com.example.utils.ResponseResult;
import com.example.utils.ResponseUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser loginUser = tokenService.getSecurityUser(request);
        if (loginUser != null) {
            // 删除用户缓存记录
            tokenService.delSecurityUser(loginUser.getToken());
        }

        ResponseUtils.result(response, ResponseResult.builder().ok("退出成功！").build());
    }
}
