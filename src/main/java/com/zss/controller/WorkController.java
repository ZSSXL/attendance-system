package com.zss.controller;

import com.github.pagehelper.PageInfo;
import com.zss.common.Const;
import com.zss.common.ResponseCode;
import com.zss.common.ServerResponse;
import com.zss.pojo.User;
import com.zss.service.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class WorkController {

    private final IWorkService iWorkService;

    @Autowired
    public WorkController(IWorkService iWorkService) {
        this.iWorkService = iWorkService;
    }

    /**
     * 签到和签退
     *
     * @param session session
     * @param sign    打卡
     * @return ServerResponse
     */
    @RequestMapping(value = "sign_in_out.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> singInOut(HttpSession session, String sign) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iWorkService.signInOut(user.getId(), sign);
    }

    /**
     * 查询个人或者比自己下一级的所有的人的考勤记录
     *
     * @param session session
     * @param choice  选择
     * @return ServerResponse
     */
    @RequestMapping(value = "select_history.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectAttendanceHistory(HttpSession session, String choice
            , @RequestParam(value = "pn", defaultValue = "1") int pn) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 判断权限
        // 如果选择查看全部的考勤记录，员工没有查看其他人的权限，因为他是最低一级
        if (choice.equals(Const.ALL_CHOICE)) {
            if (user.getRid() == 5) {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
            }
        }
        return iWorkService.selectAttendanceHistory(user.getId(), choice, pn);
    }
}
