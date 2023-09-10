package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * @Feild 用户id
     */
    private Integer userId;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * @Feild 用户名
     */
    private String username;

    /**
     * @Feild 用户密码
     */
    private String password;


    /**
     * @Feild 手机号
     */
    private String phone;

    /**
     * @Feild 邮箱号
     */
    private String email;

    /**
     * @Feild 用户性别
     */
    private Integer sex;

    /**
     * @Feild 用户年龄
     */
    private Integer age;

    /**
     * @Feild 是否已审核(0为未审核，1为审核中，2为审核通过，3为审核失败)
     */
    private Integer audited;

    /**
     * @Feild 用户是否被删除(0为未删除，1为已删除)
     */
    private Integer deleted;

    /**
     * @Feild 用户创建日期
     */
    private Timestamp createTime;

    /**
     * @Feild 用户更新日期
     */
    private Timestamp updateTime;

    /**
     * 角色
     */
    private List<Role> roleList;
}