package com.zss.service;

import com.zss.common.ServerResponse;
import com.zss.pojo.User;
import com.zss.vo.UserRoleVo;

public interface IUserService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return ServerResponse
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 查询个人信息
     *
     * @param username 用户名
     * @return ServerResponse
     */
    ServerResponse<UserRoleVo> personalList(String username);

    /**
     * 修改密码
     *
     * @param user 用户实体
     * @return ServerResponse
     */
    ServerResponse<String> changePassword(User user);
}
