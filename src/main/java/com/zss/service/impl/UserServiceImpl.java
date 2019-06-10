package com.zss.service.impl;

import com.zss.common.ServerResponse;
import com.zss.dao.UserMapper;
import com.zss.pojo.User;
import com.zss.service.IUserService;
import com.zss.vo.UserRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String username, String password){
        int rowCount = userMapper.checkUsername(username);
        if(rowCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    public ServerResponse<UserRoleVo> personalList(String username){
        UserRoleVo userRoleVo = userMapper.selectUserRole(username);
        return ServerResponse.createBySuccess(userRoleVo);
    }

    public ServerResponse<String> changePassword(User user){

        int samePassword = userMapper.isSamePassword(user.getId(), user.getPassword());
        if(samePassword > 0){
            return ServerResponse.createByErrorMessage("新老密码一致,请重新输入密码");
        }

        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if(rowCount == 0){
            return ServerResponse.createByErrorMessage("密码修改失败");
        }
        return ServerResponse.createBySuccessMessage("修改密码成功");
    }

}
