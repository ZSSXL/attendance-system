package com.zss.controller;

import com.zss.common.Const;
import com.zss.common.ResponseCode;
import com.zss.common.ServerResponse;
import com.zss.pojo.User;
import com.zss.service.IUserService;
import com.zss.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if(serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping(value = "personal_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserRoleVo> personalList(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        // 查询员工和部门
        ServerResponse<UserRoleVo> serverResponse = iUserService.personalList(user.getUsername());
        return serverResponse;
    }

    @RequestMapping(value = "change_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> changePassword(HttpSession session,String newPassword){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(newPassword.length() < 6){
            return ServerResponse.createByErrorMessage("密码字符长度不可少于六位");
        }
        System.out.println("新密码"+newPassword+":旧密码"+user.getPassword());
        user.setPassword(newPassword);
        ServerResponse<String> stringServerResponse = iUserService.changePassword(user);
        return stringServerResponse;
    }
}
