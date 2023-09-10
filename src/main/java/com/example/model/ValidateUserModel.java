package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ValidateUserModel {

    @NotNull(message = "userId不能为空")
    private Integer userId;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String validateCode;

    @NotNull(message = "主体类型不能为空")
    private int userType;

    private Timestamp updateTime = new Timestamp(new Date().getTime());
}
