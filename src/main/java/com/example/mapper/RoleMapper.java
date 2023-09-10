package com.example.mapper;

import com.example.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    void insertRole(Role role);

    Role selectRoleById(@Param("roleId") Integer roleId);

    List<Role> selectRoleList();

    void updateRoleById(Role role);

    void deleteRoleById(Role role);
}
