package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.common.ServerResponse;
import com.zss.pojo.Leave;

import java.util.List;

public interface ILeaveService {

    /**
     * 提交假条
     * @param leave
     * @return
     */
    ServerResponse<String> apply(Leave leave);

    /**
     * 查看未审核的假条
     * @param userId
     * @return
     */
    ServerResponse<PageInfo> leaveList(Integer userId,Integer pn);

    /**
     * 审核
     * @param lid
     * @param type
     * @return
     */
    ServerResponse<String> examine(Integer lid,String type);

    /**
     * 查询假条信息，除了CEO可以查所有人，其他人只能查个人
     * @param userId
     * @param pn
     * @param choice
     * @return
     */
    ServerResponse<PageInfo> selectLeave(Integer userId,Integer pn,String choice);

    /**
     * 通过lid查询假条详情
     * @param lid
     * @return
     */
    ServerResponse<Leave> showLeave(Integer lid);

    /**
     * 修改请假条
     * @param leave
     * @return
     */
    ServerResponse<String> updateLeave(Leave leave);

    /**
     * 删除请假条
     * @param leaveId
     * @return
     */
    ServerResponse<String> deleteLeave(Integer leaveId);
}
