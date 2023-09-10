package com.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChangePasswordModel {

    @NotNull(message = "userId不能为空")
    private Integer userId;

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    private String newPassword;
}
