package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectUserById(@Param("userId") int userId);

    User selectUnAuditedUserById(@Param("userId") int userId);

    User selectUserByUsername(@Param("username") String username);

    User selectUserByPhone(@Param("phone") String phone);

    User selectUserByEmail(@Param("email") String email);

    List<User> selectUserListByKeyword(@Param("keyword") String keyword, @Param("audited") Integer audited, @Param("createTime") String createTime);

    void updateUserInfoById(User user);

    void deleteUserById(@Param("userId") int userId);
}
