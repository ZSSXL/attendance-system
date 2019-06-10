package com.zss.service;

import com.github.pagehelper.PageInfo;
import com.zss.common.ServerResponse;

public interface IWorkService {

    /**
     * 签到&签退
     * @param userId
     * @param sign
     * @return
     */
    ServerResponse<String> signInOut(Integer userId, String sign);

    /**
     * 根据选择查询个人还是下一级所有人的考勤记录
     * @param userId
     * @param choice
     * @param pn
     * @return
     */
    ServerResponse<PageInfo> selectAttendanceHistory(Integer userId, String choice, Integer pn);
}
