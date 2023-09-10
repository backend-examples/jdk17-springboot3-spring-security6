package com.example.service.impl;


import com.example.constant.SecurityConstant;
import com.example.model.SecurityUser;
import com.example.properties.TokenProperties;
import com.example.redis.LoginRepository;
import com.example.service.TokenService;
import com.example.utils.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private TokenProperties tokenProperties;

    @Resource
    private LoginRepository loginRepository;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Override
    public SecurityUser getSecurityUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(SecurityConstant.LOGIN_USER_KEY);
                SecurityUser securityUser = loginRepository.getLoginUser(uuid);

                return securityUser;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public SecurityUser getSecurityUser(String authorization) {
        String token = getToken(authorization);

        if (StringUtils.isNotEmpty(token)) {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(SecurityConstant.LOGIN_USER_KEY);
                SecurityUser securityUser = loginRepository.getLoginUser(uuid);

                return securityUser;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void setSecurityUser(SecurityUser securityUser) {
        if (securityUser != null && StringUtils.isNotEmpty(securityUser.getToken())) {
            refreshToken(securityUser);
        }
    }

    @Override
    public void delSecurityUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            loginRepository.delLoginUser(token);
        }
    }

    @Override
    public String createToken(SecurityUser securityUser) {
        String token = IdUtils.fastUUID();
        securityUser.setToken(token);
        refreshToken(securityUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstant.LOGIN_USER_KEY, token);

        return createToken(claims);
    }

    @Override
    public void verifyToken(SecurityUser securityUser) {
        long expireTime = securityUser.getExpireTime();
        long currentTime = System.currentTimeMillis();

        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(securityUser);
        }
    }

    @Override
    public void refreshToken(SecurityUser securityUser) {
        securityUser.setLoginTime(System.currentTimeMillis());
        securityUser.setExpireTime(securityUser.getLoginTime() + tokenProperties.getExpireTime() * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        loginRepository.saveLoginUser(securityUser.getToken(), securityUser, tokenProperties.getExpireTime());
    }

    @Override
    public String getUserKeyFromToken(String token) {
        Claims claims = parseToken(getToken(token));
        return (String) claims.get(SecurityConstant.LOGIN_USER_KEY);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret()).compact();

        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenProperties.getHeader());
        if (StringUtils.isNotEmpty(token) && token.toUpperCase().startsWith(SecurityConstant.TOKEN_PREFIX.toUpperCase())) {
            token = token.replaceFirst(String.format("(?i)%s", SecurityConstant.TOKEN_PREFIX), "");
        }

        return token;
    }

    /**
     * 获取请求token
     *
     * @param token
     * @return token
     */
    private String getToken(String token) {
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        }

        return token;
    }
}
