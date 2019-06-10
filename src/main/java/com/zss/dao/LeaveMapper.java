package com.zss.dao;

import com.zss.pojo.Leave;
import com.zss.vo.LeaveUserRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Leave record);

    int insertSelective(Leave record);

    Leave selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Leave record);

    int updateByPrimaryKeyWithBLOBs(Leave record);

    int updateByPrimaryKey(Leave record);

    List<LeaveUserRoleVo> selectLeaveByUserId(@Param("userId") Integer userId);

    int updateStatusByLid(@Param("status") String status,@Param("lid") Integer lid);

    List<LeaveUserRoleVo> selectLeaveByUserIdRole(@Param("userId") Integer userId,@Param("choice") String choice);

}