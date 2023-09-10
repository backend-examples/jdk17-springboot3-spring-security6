package com.example.model;


import com.example.pojo.User;
import com.example.serializer.SimpleGrantedAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 公众号：码猿技术专栏
 * 存储用户的详细信息，实现UserDetails，后续有定制的字段可以自己拓展
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户登录时间
     */
    private Long loginTime;

    /**
     * token失效时间
     */
    private Long expireTime;

    /**
     * 审核状态
     */
    private Integer audited;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 角色列表
     */
    private List<String> roles;

    /**
     * 权限+角色集合
     */
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public SecurityUser(){}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未被锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
