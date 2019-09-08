package com.zss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zss.common.Const;
import com.zss.common.ServerResponse;
import com.zss.dao.LeaveMapper;
import com.zss.pojo.Leave;
import com.zss.service.ILeaveService;
import com.zss.vo.LeaveUserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("iLeaveService")
public class LeaveServiceImpl implements ILeaveService {

    private final LeaveMapper leaveMapper;

    @Autowired
    public LeaveServiceImpl(LeaveMapper leaveMapper) {
        this.leaveMapper = leaveMapper;
    }

    public ServerResponse<String> apply(Leave leave) {
        int rowCount = leaveMapper.insert(leave);
        if (rowCount > 0) {
            log.info("假条已经提交");
            return ServerResponse.createBySuccessMessage("假条已提交");
        }
        log.info("假条提交失败");
        return ServerResponse.createByErrorMessage("假条提交失败");
    }

    public ServerResponse<PageInfo> leaveList(Integer userId, Integer pn) {
        PageHelper.startPage(pn, 8); // 默认一页有显示八条记录
        List<LeaveUserRoleVo> leaveUserRoleVoList = leaveMapper.selectLeaveByUserId(userId);
        if (leaveUserRoleVoList == null) {
            return ServerResponse.createByErrorMessage("没有未审核的假条");
        }
        // 分页处理
        PageInfo pageInfo = new PageInfo(leaveUserRoleVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<String> examine(Integer lid, String type) {
        if (Const.EXAMINE_STATUS_PASS.equals(type)) {
            // 审核通过
            int rowCount = leaveMapper.updateStatusByLid(Const.EXAMINE_STATUS_PASS, lid);
            if (rowCount > 0) {
                log.info("审核成功");
                return ServerResponse.createBySuccessMessage("审核成功");
            }
        } else if (Const.EXAMINE_STATUS_DISPASS.equals(type)) {
            // 审核不通过
            int rowCount = leaveMapper.updateStatusByLid(Const.EXAMINE_STATUS_DISPASS, lid);
            if (rowCount > 0) {
                log.info("审核失败");
                return ServerResponse.createBySuccessMessage("审核成功");
            }
        }
        return null;
    }

    public ServerResponse<PageInfo> selectLeave(Integer userId, Integer pn, String choice) {
        PageHelper.startPage(pn, 8); // 默认一页有显示八条记录
        List<LeaveUserRoleVo> leaveUserRoleVoList = leaveMapper.selectLeaveByUserIdRole(userId, choice);
        if (leaveUserRoleVoList == null) {
            return ServerResponse.createByErrorMessage("没有请假记录");
        }
        // 分页处理
        PageInfo pageInfo = new PageInfo(leaveUserRoleVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<Leave> showLeave(Integer lid) {
        Leave leave = leaveMapper.selectByPrimaryKey(lid);
        if (leave == null) {
            log.info("假条查询失败");
            return ServerResponse.createByErrorMessage("查询出错");
        }
        return ServerResponse.createBySuccess(leave);
    }

    public ServerResponse<String> updateLeave(Leave leave) {
        int rowCount = leaveMapper.updateByPrimaryKeySelective(leave);
        if (rowCount == 0) {
            log.error("假条修改失败");
            return ServerResponse.createByErrorMessage("修改失败");
        }
        log.info("假条修改成功");
        return ServerResponse.createBySuccessMessage("修改成功");
    }

    public ServerResponse<String> deleteLeave(Integer leaveId) {
        int rowCount = leaveMapper.deleteByPrimaryKey(leaveId);
        if (rowCount == 0) {
            log.error("假条删除失败");
            return ServerResponse.createByErrorMessage("删除失败");
        }
        log.info("假条删除成功");
        return ServerResponse.createBySuccessMessage("成功删除");
    }
}
