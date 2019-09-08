package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.common.ServerResponse;
import com.zss.pojo.Leave;

public interface ILeaveService {

    /**
     * 提交假条
     *
     * @param leave 假条实体
     * @return ServerResponse
     */
    ServerResponse<String> apply(Leave leave);

    /**
     * 查看未审核的假条
     *
     * @param userId 用户id
     * @return ServerResponse
     */
    ServerResponse<PageInfo> leaveList(Integer userId, Integer pn);

    /**
     * 审核
     *
     * @param lid  假条id
     * @param type 类型
     * @return ServerResponse
     */
    ServerResponse<String> examine(Integer lid, String type);

    /**
     * 查询假条信息，除了CEO可以查所有人，其他人只能查个人
     *
     * @param userId 用户id
     * @param pn     当前页
     * @param choice 选择
     * @return ServerResponse
     */
    ServerResponse<PageInfo> selectLeave(Integer userId, Integer pn, String choice);

    /**
     * 通过lid查询假条详情
     *
     * @param lid 假条id
     * @return ServerResponse
     */
    ServerResponse<Leave> showLeave(Integer lid);

    /**
     * 修改请假条
     *
     * @param leave 假条实体
     * @return ServerResponse
     */
    ServerResponse<String> updateLeave(Leave leave);

    /**
     * 删除请假条
     *
     * @param leaveId 假条id
     * @return ServerResponse
     */
    ServerResponse<String> deleteLeave(Integer leaveId);
}
