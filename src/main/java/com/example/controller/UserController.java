package com.example.controller;

import com.example.annotation.Pagination;
import com.example.model.ChangePasswordModel;
import com.example.model.EditUserModel;
import com.example.model.SecurityUser;
import com.example.model.ValidateUserModel;
import com.example.service.UserService;
import com.example.utils.ResponseResult;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/certifiedUser")
    public ResponseResult certifiedUser(@RequestBody ValidateUserModel validateUserModel) {
        userService.validateUser(validateUserModel);

        return ResponseResult.builder().ok("手机号已通过认证！").build();
    }

    @GetMapping("/registerInfo")
    public ResponseResult registerInfo(@RequestHeader("Authorization") String token) {

        return ResponseResult.builder().ok().build();
    }

    @GetMapping("/getUserInfo")
    public ResponseResult getUserInfo(@RequestHeader("Authorization") String token) {
        SecurityUser securityUser = userService.getUserInfoByToken(token);

        Map<String, Object> resultMap = new HashMap<>(4);
        Map<String, Object> userInfo = new HashMap<>(4);
        userInfo.put("userId", securityUser.getUserId());
        userInfo.put("username", securityUser.getUsername());
        userInfo.put("phone", securityUser.getPhone());
        userInfo.put("email", securityUser.getEmail());
        userInfo.put("sex", securityUser.getUser().getSex());
        userInfo.put("role", securityUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        userInfo.put("audited", securityUser.getAudited());
        userInfo.put("createTime", securityUser.getUser().getCreateTime());

        resultMap.put("userInfo", userInfo);

        return ResponseResult.builder().ok(resultMap).build();
    }

    @PreAuthorize("hasAnyRole('superAdmin', 'admin', 'user')")
    @Pagination(targetParameter = "userList")
    @GetMapping("/getUserListByKeyword")
    public ResponseResult getUserListByKeyword(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer audited, @RequestParam(required = false) Long createTime) {
        List<SecurityUser> userList = userService.getUserListByKeyword(keyword, audited, createTime);

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("userList", userList);

        return ResponseResult.builder().ok(resultMap).build();
    }

    @PatchMapping("/editUserById")
    public ResponseResult editUserById(@Valid @RequestBody EditUserModel editUserModel) {

        userService.editUserById(editUserModel);

        return ResponseResult.builder().ok().build();
    }

    @PreAuthorize("hasAnyRole('superAdmin', 'admin')")
    @GetMapping("/getUserById/{userId}")
    public ResponseResult getUserById(@PathVariable Integer userId) {

        SecurityUser securityUser = userService.getUserById(userId);
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("user", securityUser);

        return ResponseResult.builder().ok(resultMap).build();
    }

    @PatchMapping("/changePassword")
    public ResponseResult changePassword(@Valid @RequestBody ChangePasswordModel changePasswordModel) {
        userService.changePasswordById(changePasswordModel);

        return ResponseResult.builder().ok("密码修改成功！").build();
    }
}
