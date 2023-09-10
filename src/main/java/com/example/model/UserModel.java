package com.example.model;


import com.example.enums.AuditEnum;
import com.example.enums.DeleteEnum;
import com.example.enums.SexEnum;
import com.example.pojo.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class UserModel {

    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String phone;

    @NotBlank(message = "邮箱号不能为空")
    private String email;

    /**
     * 用户角色：默认为2, ROLE_user
     */
    private Integer roleId = 2;

    /**
     * 用户身份是否已被审核通过，默认为0，表示未进行审核
     */
    private Integer audited = AuditEnum.unAudit.ordinal();

    private Integer deleted = DeleteEnum.undelete.ordinal();

    private final Timestamp dateTime = new Timestamp(new Date().getTime());

    private Timestamp createTime = dateTime;

    private Timestamp updateTime = dateTime;

    private String validateCode;

    @SneakyThrows
    public User toUser(UserModel userModel) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername(userModel.getUsername() != null ? userModel.getUsername() : userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setPhone(userModel.getPhone());
        user.setEmail(userModel.getEmail());
        user.setSex(SexEnum.MAN.ordinal());
        user.setAge(0);
        user.setAudited(userModel.getAudited());
        user.setDeleted(userModel.getDeleted());
        user.setCreateTime(userModel.getCreateTime());
        user.setUpdateTime(userModel.getUpdateTime());

        return user;
    }
}
