package com.example.service.impl;

import com.example.constant.MessagesConstant;
import com.example.enums.CodeEnum;
import com.example.exception.BizException;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.model.SecurityUser;
import com.example.model.UserModel;
import com.example.pojo.User;
import com.example.pojo.UserRole;
import com.example.service.AuthService;
import com.example.service.TokenService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, Object> login(UserModel userModel) {
        String username;

        if (userModel.getUsername() != null) {
            username = userModel.getUsername();
        } else if (userModel.getPhone() != null) {
            username = userModel.getPhone();
        } else if (userModel.getEmail() != null) {
            username = userModel.getEmail();
        } else  {
            throw new BizException("用户名不能为空");
        }

        // 用户验证
        Authentication authentication = null;
        try {
            // 下面操作主要用于登录的时候方便全局获取用户认证信息。而TokenAuthenticationFilter中同样的操作，则是方便登录后全局使用认证对象去获取用户信息。
            // 这里存储的是从前端发送过来的用户和密码
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, userModel.getPassword());
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            // BadCredentialsException 异常用来抛出用户名或密码错误
            if (e instanceof BadCredentialsException) {
                throw new BizException(CodeEnum.USERNAME_PASSWORD_ERROR);
            } else {
                throw new BizException(e.getMessage());
            }
        }  finally {
            // 无论结果如何，清除全局上下文
            SecurityContextHolder.clearContext();
        }

        Map<String, Object> resultMap = new HashMap<>();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        // 生成token
        String accessToken = tokenService.createToken(securityUser);
        resultMap.put("accessToken", accessToken);

        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> registerUser(UserModel userModel) {
        User user = userModel.toUser(userModel);


        User exitedUser = userMapper.selectUserByUsername(user.getUsername());
        if (exitedUser != null) {
            throw new BizException(MessagesConstant.ACCOUNT_HAS_EXIST);
        }

        userMapper.insertUser(user);

        UserRole roleUser = new UserRole();
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(userModel.getRoleId());

        userRoleMapper.insertUserRole(roleUser);

        Map<String, Object> resultMap = new HashMap<>(4);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId(user.getUserId());

        String accessToken = tokenService.createToken(securityUser);
        resultMap.put("token", accessToken);

        return resultMap;
    }
}
