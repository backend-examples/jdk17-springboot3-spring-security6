package com.example.constant;

public class SecurityConstant {

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens";

    /**
     * 令牌中用户信息的键
     */
    public static final String LOGIN_USER_KEY = "login_user_key";
}
