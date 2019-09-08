package com.zss.controller;

import com.zss.common.Const;
import com.zss.common.ResponseCode;
import com.zss.common.ServerResponse;
import com.zss.pojo.User;
import com.zss.service.IUserService;
import com.zss.vo.UserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author ZSS
 * @description user controller
 */
@Slf4j
@Controller
public class UserController {

    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @param session  session
     * @return ServerResponse
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
            log.info("登录成功,成功保存session");
        }
        return serverResponse;
    }

    /**
     * 用户列表
     *
     * @param session session
     * @return ServerResponse
     */
    @RequestMapping(value = "personal_list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserRoleVo> personalList(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 查询员工和部门
        return iUserService.personalList(user.getUsername());
    }

    /**
     * 修改密码
     *
     * @param session     session
     * @param newPassword 新密码
     * @return ServerResponse
     */
    @RequestMapping(value = "change_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> changePassword(HttpSession session, String newPassword) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (newPassword.length() < 6) {
            return ServerResponse.createByErrorMessage("密码字符长度不可少于六位");
        }
        user.setPassword(newPassword);
        return iUserService.changePassword(user);
    }
}
