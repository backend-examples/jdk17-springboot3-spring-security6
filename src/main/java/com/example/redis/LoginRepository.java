package com.example.redis;


import com.example.model.SecurityUser;

public interface LoginRepository {

    /**
     * 保存短信验证码
     * @param phoneOrEmail
     * @param code
     */
    void saveSmsCode(String phoneOrEmail, String code);

    /**
     * 获取短信验证码
     * @param phoneOrEmail
     * @return
     */
    String getSmsCode(String phoneOrEmail);

    /**
     * 删除短信验证码
     * @param phoneOrEmail
     */
    void delSmsCode(String phoneOrEmail);

    /**
     * 保存图形验证码
     * @param code
     */
    void saveCaptchaCode(String code);

    /**
     * 获取图形验证码
     * @return
     */
    String getCaptchaCode();

    /**
     * 删除图形验证码
     */
    void delCaptchaCode();

    /**
     * 存储邮件链接地址
     * @param email
     */
    void setEmailLink(String email, String emailLink);

    /**
     * 获取邮件链接地址
     * @param email
     * @return
     */
    String getEmailLink(String email);

    /**
     * 删除邮件链接地址
     * @param email
     */
    void delEmailLink(String email);

    /**
     * 保存登录用户信息
     *
     * @param key
     * @param securityUser
     */
    void saveLoginUser(String key, SecurityUser securityUser, Long time);

    /**
     * 获取登录用户信息
     * @param key
     * @return
     */
    SecurityUser getLoginUser(String key);

    /**
     * 删除登录用户信息
     *
     * @param key
     */
    void delLoginUser(String key);
}
