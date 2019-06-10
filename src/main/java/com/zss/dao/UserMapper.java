package com.zss.dao;

import com.zss.pojo.User;
import com.zss.vo.UserRoleVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    UserRoleVo selectUserRole(String username);

    int isSamePassword(@Param("userId") Integer userId,@Param("password") String password);
}