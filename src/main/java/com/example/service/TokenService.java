package com.example.service;

import com.example.model.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    SecurityUser getSecurityUser(HttpServletRequest request);

    SecurityUser getSecurityUser(String token);

    /**
     * 设置用户身份信息
     */
    void setSecurityUser(SecurityUser securityUser);

    /**
     * 删除用户身份信息
     */
    void delSecurityUser(String token);

    /**
     * 创建令牌
     *
     * @param securityUser 用户信息
     * @return 令牌
     */
    String createToken(SecurityUser securityUser);

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param securityUser
     * @return 令牌
     */
    void verifyToken(SecurityUser securityUser);

    /**
     * 刷新令牌有效期
     *
     * @param securityUser 登录信息
     */
    void refreshToken(SecurityUser securityUser);

    /**
     * 获取登录用户的redis key
     * @param token
     * @return
     */
    String getUserKeyFromToken(String token);
}
