package com.zss.controller;

import com.github.pagehelper.PageInfo;
import com.zss.common.Const;
import com.zss.common.ResponseCode;
import com.zss.common.ServerResponse;
import com.zss.pojo.Leave;
import com.zss.pojo.User;
import com.zss.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author ZSS
 * @description leave controller
 */
@Controller
public class LeaveController {

    private final ILeaveService iLeaveService;

    @Autowired
    public LeaveController(ILeaveService iLeaveService) {
        this.iLeaveService = iLeaveService;
    }

    /**
     * 申请请假
     *
     * @return ServerResponse
     */
    @RequestMapping(value = "apply.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> apply(HttpSession session, String needTime, String leaveTime, String backTime, String reason) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 填充业务
        Leave leave = assemmbleLeave(user.getId(), needTime, leaveTime, backTime, reason);
        return iLeaveService.apply(leave);
    }


    @RequestMapping(value = "leave_list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> leaveList(HttpSession session, @RequestParam(value = "pn", defaultValue = "1") int pn) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 填充业务
        // 上一级的领导可以审核下一级的人的假条,不可以审核查本身
        return iLeaveService.leaveList(user.getId(), pn);
    }

    /**
     * 审核请假通过或者不通过
     *
     * @param lid  假单号
     * @param type 操作类型，pass未通过，dispass未不通过
     * @return ServerResponse
     */
    @RequestMapping(value = "pass_dispass.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> examine(String lid, String type) {
        return iLeaveService.examine(Integer.valueOf(lid), type);
    }

    /**
     * @param session session
     * @param pn      当前页
     * @param choice  选择查询个人还是所有人，只有CEO可以查询受有人，默认为个人，personal & all
     * @return ServerResponse
     */
    @RequestMapping(value = "select_leave.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> selectLeave(HttpSession session, @RequestParam(value = "pn", defaultValue = "1") int pn
            , @RequestParam(value = "choice", defaultValue = Const.PERSONAL_CHOICE) String choice) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 填充业务
        // 查询个人的假条信息，admin不仅可以查自己的，还可以选择查所有人的
        return iLeaveService.selectLeave(user.getId(), pn, choice);
    }

    /**
     * 将一条请假记录显示在模态框 通过假单id查看
     *
     * @param lid 假条id
     * @return ServerResponse
     */
    @RequestMapping(value = "show_leave.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Leave> showLeave(Integer lid) {
        return iLeaveService.showLeave(lid);
    }

    /**
     * 更新修改请假单，如果已经审核的不允许修改，只能修改untreated状态的
     *
     * @param leaveId   假条id
     * @param needTime  需要时间
     * @param leaveTime 离开时间
     * @param backTime  返回时间
     * @param reason    理由
     * @return ServerResponse
     */
    @RequestMapping(value = "update_leave.do", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse<String> updateLeave(Integer leaveId, String needTime, String leaveTime, String backTime, String reason) {
        Leave leave = new Leave();
        leave.setId(leaveId);
        leave.setNeedTime(needTime);
        leave.setLeaveTime(leaveTime);
        leave.setBackTime(backTime);
        leave.setReason(reason);
        return iLeaveService.updateLeave(leave);
    }

    /**
     * 删除假条
     *
     * @param leaveId 假条id
     * @return ServerResponse
     */
    @RequestMapping(value = "delete_leave.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteLeave(Integer leaveId) {
        return iLeaveService.deleteLeave(leaveId);
    }


    /**
     * 内部私有方法
     *
     * @param userId    用户id
     * @param needTime  需要时间
     * @param leaveTime 离开时间
     * @param backTime  返回时间
     * @param reason    理由
     * @return Leave
     */
    private Leave assemmbleLeave(Integer userId, String needTime, String leaveTime, String backTime, String reason) {
        Leave leave = new Leave();
        leave.setUid(userId);
        leave.setNeedTime(needTime);
        leave.setLeaveTime(leaveTime);
        leave.setBackTime(backTime);
        leave.setReason(reason);
        leave.setStatus(Const.UNTREATED); // 假条状态初始化未未处理untreated
        return leave;
    }
}
