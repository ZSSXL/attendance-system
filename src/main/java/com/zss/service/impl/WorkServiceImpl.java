package com.zss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.common.Const;
import com.zss.common.ServerResponse;
import com.zss.dao.WorkMapper;
import com.zss.service.IWorkService;
import com.zss.util.DateUtil;
import com.zss.vo.WorkUserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("iWorkService")
public class WorkServiceImpl implements IWorkService {

    private final WorkMapper workMapper;

    @Autowired
    public WorkServiceImpl(WorkMapper workMapper) {
        this.workMapper = workMapper;
    }

    public ServerResponse<String> signInOut(Integer userId, String sign) {
        if (StringUtils.isNotBlank(sign)) {
            // 获取当前日期
            String currentDay = DateUtil.whichDay();
            String currentHour = DateUtil.whichHour();
            int rowCount = workMapper.selectSignToday(userId, currentDay);
            if (Const.SIGN_IN.equals(sign)) {
                // 签到
                if (rowCount == 0) {
                    workMapper.insertSignIn(userId, currentDay, currentHour);
                    log.info("签到成功");
                    return ServerResponse.createBySuccessMessage("签到成功");
                }
                log.info("今天已经签到");
                return ServerResponse.createByErrorMessage("今天已经签到");
            } else if (Const.SING_OUT.equals(sign)) {
                // 签退
                if (rowCount == 0) {
                    log.info("今天未签到");
                    return ServerResponse.createByErrorMessage("当天未签到，不能签退");
                }
                if (rowCount > 0) {
                    workMapper.updateSingOut(userId, currentDay, currentHour);
                    log.info("签退成功");
                    return ServerResponse.createBySuccessMessage("签退成功");
                }
            }
        }
        return ServerResponse.createByErrorMessage("参数错误");
    }

    public ServerResponse<PageInfo> selectAttendanceHistory(Integer userId, String choice, Integer pn) {
        PageHelper.startPage(pn, 8);
        List<WorkUserRoleVo> workUserRoleVoList = workMapper.selectHistoryBySelective(userId, choice);
        if (workUserRoleVoList == null) {
            log.info("没有考勤记录");
            return ServerResponse.createBySuccessMessage("没有考勤记录");
        }
        PageInfo pageInfo = new PageInfo(workUserRoleVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
