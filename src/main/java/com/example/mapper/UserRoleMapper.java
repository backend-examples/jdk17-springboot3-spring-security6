package com.example.mapper;

import com.example.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void insertUserRole(UserRole roleUser);

    List<UserRole> selectUserRoleByUserId(@Param("userId") Integer userId);

    List<UserRole> selectUserRoleByRoleId(@Param("roleId") Integer roleId);

    void deleteUserRoleByUserId(@Param("userId") int userId);
}
