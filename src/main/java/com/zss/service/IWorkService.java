package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.common.ServerResponse;

public interface IWorkService {

    /**
     * 签到&签退
     *
     * @param userId 用户id
     * @param sign   打卡
     * @return ServerResponse
     */
    ServerResponse<String> signInOut(Integer userId, String sign);

    /**
     * 根据选择查询个人还是下一级所有人的考勤记录
     *
     * @param userId 用户id
     * @param choice 选择
     * @param pn     当前页面
     * @return ServerResponse
     */
    ServerResponse<PageInfo> selectAttendanceHistory(Integer userId, String choice, Integer pn);
}
