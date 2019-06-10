package com.zss.dao;

import com.zss.pojo.Work;
import com.zss.vo.WorkUserRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Work record);

    int insertSelective(Work record);

    Work selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Work record);

    int updateByPrimaryKey(Work record);

    int selectSignToday(@Param("userId") Integer userId, @Param("currentDate") String currentDate);

    void insertSignIn(@Param("userId") Integer userId, @Param("currentDay") String currentDay,@Param("currentHour") String currentHour);

    void updateSingOut(@Param("userId") Integer userId, @Param("currentDay") String currentDay,@Param("currentHour") String currentHour);

    List<WorkUserRoleVo> selectHistoryBySelective(@Param("userId") Integer userId,@Param("choice") String choice);
}