package com.example.pojo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Role {

    /**
     * @Feild 角色id
     */
    private Integer roleId;

    /**
     * @Feild 角色名
     */
    private String roleName;

    /**
     * @Feild 角色备注
     */
    private String remark;

    /**
     * 删除状态：0为未删除，1为已删除
     */
    private Integer delFlag;

    private Timestamp createTime;

    private Timestamp updateTime;
}

