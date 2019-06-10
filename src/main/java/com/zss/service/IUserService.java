package com.zss.service;

import com.zss.common.ServerResponse;
import com.zss.pojo.User;
import com.zss.vo.UserRoleVo;

public interface IUserService {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 查询个人信息
     * @param username
     * @return
     */
    ServerResponse<UserRoleVo> personalList(String username);

    /**
     * 修改密码
     * @param user
     * @return
     */
    ServerResponse<String> changePassword(User user);
}
