package com.example.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRole {

    /**
     * @Feild 角色id
     */
    private Integer roleId;

    /**
     * @Feild 用户id
     */
    private Integer userId;
}
