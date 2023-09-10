package com.example.redis.impl;

import com.example.constant.RedisConstant;
import com.example.constant.SecurityConstant;
import com.example.enums.CodeEnum;
import com.example.exception.BizException;
import com.example.model.SecurityUser;
import com.example.redis.LoginRepository;
import com.example.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class LoginRepositoryImpl implements LoginRepository {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void saveSmsCode(String phoneOrEmail, String code) {
        redisUtil.set(buildKey(RedisConstant.REDIS_UMS_PREFIX, phoneOrEmail), code, RedisConstant.REDIS_UMS_EXPIRE);
    }

    @Override
    public String getSmsCode(String phoneOrEmail) {
        return redisUtil.get(buildKey(RedisConstant.REDIS_UMS_PREFIX, phoneOrEmail));
    }

    @Override
    public void delSmsCode(String phoneOrEmail) {
        redisUtil.del(buildKey(RedisConstant.REDIS_UMS_PREFIX, phoneOrEmail));
    }

    @Override
    public void saveCaptchaCode(String code) {
        redisUtil.set(RedisConstant.REDIS_UMS_PREFIX, code, RedisConstant.REDIS_UMS_EXPIRE);
    }

    @Override
    public String getCaptchaCode() {
        return (String) redisUtil.get(RedisConstant.REDIS_UMS_PREFIX);
    }

    @Override
    public void delCaptchaCode() {
        redisUtil.del(RedisConstant.REDIS_UMS_PREFIX);
    }

    @Override
    public void setEmailLink(String email, String emailLink) {
        redisUtil.set(buildKey(RedisConstant.EMAIL_EXPIRE_PREFIX, email), emailLink, RedisConstant.EMAIL_EXPIRE_TIME);
    }

    @Override
    public String getEmailLink(String email) {
        return redisUtil.get(buildKey(RedisConstant.EMAIL_EXPIRE_PREFIX, email));
    }

    @Override
    public void delEmailLink(String email) {
        redisUtil.del(buildKey(RedisConstant.EMAIL_EXPIRE_PREFIX, email));
    }

    @Override
    public void saveLoginUser(String key, SecurityUser securityUser, Long time) {
        redisUtil.set(buildKey(SecurityConstant.LOGIN_TOKEN_KEY, key), securityUser, time);
    }

    @Override
    public SecurityUser getLoginUser(String key) {
        return redisUtil.get(buildKey(SecurityConstant.LOGIN_TOKEN_KEY, key));
    }

    @Override
    public void delLoginUser(String key) {
        redisUtil.del(buildKey(SecurityConstant.LOGIN_TOKEN_KEY, key));
    }

    private String buildKey(String prefix, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new BizException(CodeEnum.BODY_NOT_MATCH);
        }

        return prefix + ":" + key;
    }
}
