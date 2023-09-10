package com.example.service.impl;

import com.example.constant.MessagesConstant;
import com.example.enums.CodeEnum;
import com.example.exception.BizException;
import com.example.mapper.UserMapper;
import com.example.model.*;
import com.example.pojo.Role;
import com.example.pojo.User;
import com.example.redis.LoginRepository;
import com.example.service.TokenService;
import com.example.service.UserService;
import com.example.utils.DateUtil;
import com.example.utils.RegexUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private LoginRepository loginRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsernameOrPhoneOrEmail(username);

        if (user == null) {
            // 用户不存在直接抛出UsernameNotFoundException，security会捕获抛出BadCredentialsException
            // 替换为下面的异常，可以被认证失败处理器捕获到
            throw new InternalAuthenticationServiceException(username + "不存在！");
        }

        SecurityUser securityUser = userConvertSecurityUser(user);

        return securityUser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validateUser(ValidateUserModel validateUserModel) {
        String redisValidateCode = loginRepository.getSmsCode(validateUserModel.getPhone());

        if (redisValidateCode == null) {
            throw new BizException("验证码已经过期或尚未发送，请重新发送验证码！");
        }

        if (!validateUserModel.getValidateCode().equalsIgnoreCase(redisValidateCode)) {
            throw new BizException("验证码错误！");
        }

        User user = userMapper.selectUserById(validateUserModel.getUserId());
        if (user == null) {
            throw new BizException(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), MessagesConstant.ACCOUNT_NOT_EXIST);
        }

        user.setPhone(validateUserModel.getPhone());
        user.setUpdateTime(validateUserModel.getUpdateTime());

        userMapper.updateUserInfoById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserById(EditUserModel editUserModel) {
        User user = userMapper.selectUserById(editUserModel.getUserId());

        if (user == null) {
            throw new BizException("该用户不存在！");
        }

        user.setUsername(editUserModel.getUsername());
        user.setPhone(editUserModel.getPhone());
        user.setEmail(editUserModel.getEmail());
        user.setSex(editUserModel.getSex());
        user.setUpdateTime(new Timestamp(new Date().getTime()));

        userMapper.updateUserInfoById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePasswordById(ChangePasswordModel changePasswordModel) {
        User user = userMapper.selectUserById(changePasswordModel.getUserId());

        if (user == null) {
            throw new BizException("该用户不存在！");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(changePasswordModel.getOldPassword(), user.getPassword())) {
            throw new BizException("原密码错误！");
        }

        user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
        user.setUpdateTime(new Timestamp(new Date().getTime()));

        userMapper.updateUserInfoById(user);
    }

    @Override
    public boolean checkPhoneOrEmail(String usernameOrPhoneOrEmail) {
        return false;
    }

    @Override
    public SecurityUser getUserInfoByToken(String token) {
        return null;
    }

    @Override
    public List<SecurityUser> getUserListByKeyword(String keyword, Integer audited, Long createTime) {
        String createTimestamp = null;
        if (createTime != null) {
            createTimestamp = DateUtil.formatLongToString(createTime, DateUtil.FORMAT_yyyy_MM_dd);
        }

        List<User> userList = userMapper.selectUserListByKeyword(keyword, audited, createTimestamp);

        List<SecurityUser> securityUserList = new ArrayList<>(4);

        for (int i=0; i<userList.size(); i++) {
            SecurityUser securityUser = userConvertSecurityUser(userList.get(i));

            securityUserList.add(securityUser);
        }

        return securityUserList;
    }

    @Override
    public SecurityUser getUserById(Integer userId) {
        User user = userMapper.selectUserById(userId);
        if (user == null) {
            throw new BizException("用户不存在！");
        }

        SecurityUser securityUser = userConvertSecurityUser(user);

        return securityUser;
    }

    public User getUserByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail) {
        User user;
        /**
         * 因为刷新token也使用了loadUserByUsername方法
         * 所以在这里判断是手机号、邮箱、还是普通用户名
         */
        if (RegexUtils.checkMobile(usernameOrPhoneOrEmail)) {
            user = userMapper.selectUserByPhone(usernameOrPhoneOrEmail);
        } else if (RegexUtils.checkEmail(usernameOrPhoneOrEmail)) {
            user = userMapper.selectUserByEmail(usernameOrPhoneOrEmail);
        } else {
            user = userMapper.selectUserByUsername(usernameOrPhoneOrEmail);
        }

        return user;
    }

    public SecurityUser userConvertSecurityUser(User user) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId(user.getUserId());
        securityUser.setUsername(user.getUsername());
        securityUser.setPassword(user.getPassword());
        securityUser.setPhone(user.getPhone());
        securityUser.setEmail(user.getEmail());
        securityUser.setAudited(user.getAudited());
        securityUser.setUser(user);
        String[] authoritiesArray = {};

        //获取权限集合
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRoleList().stream().map(Role::getRoleName).collect(Collectors.toSet()).toArray(authoritiesArray));
        securityUser.setAuthorities(authorities);
        securityUser.setRoles(user.getRoleList().stream().map(Role::getRoleName).collect(Collectors.toList()));

        return securityUser;
    }
}
