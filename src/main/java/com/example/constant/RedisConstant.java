package com.example.constant;

public class RedisConstant {
    /**
     * 为了保证 redis 的key不会因为 不同的业务可能会相同  所以一般会在这里加上前缀
     */
    public static final String REDIS_UMS_PREFIX = "portal:authCode";

    /**
     * redis缓存过期时间 我们设置为 5分钟  一般验证码时间防止暴力破解 时间都很短
     */
    public static final Long REDIS_UMS_EXPIRE = 60 * 5L;

    /**
     * email邮件的链接的redis的key
     */
    public static final String EMAIL_EXPIRE_PREFIX = "expire:email";

    /**
     * 邮件链接的过期时长
     */
    public static final Long EMAIL_EXPIRE_TIME = 60 * 10L;
}
