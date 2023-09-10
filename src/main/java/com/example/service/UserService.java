package com.example.service;

import com.example.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    void validateUser(ValidateUserModel validateUserModel);

    void editUserById(EditUserModel editUserModel);

    void changePasswordById(ChangePasswordModel changePasswordModel);

    boolean checkPhoneOrEmail(String usernameOrPhoneOrEmail);

    SecurityUser getUserInfoByToken(String token);

    List<SecurityUser> getUserListByKeyword(String keyword, Integer audited, Long createTime);

    SecurityUser getUserById(Integer userId);
}
