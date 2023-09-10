package com.example.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserModel {

    @NotNull(message = "userId不能为空")
    private Integer userId;

    private String username;

    private String phone;

    private String email;

    @NotNull(message = "性别不能为空")
    private Integer sex;
}
