package com.zss.test;

import com.github.pagehelper.PageInfo;
import com.zss.common.ServerResponse;
import com.zss.dao.LeaveMapper;
import com.zss.pojo.Leave;
import com.zss.service.ILeaveService;
import com.zss.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ServiceTest {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ILeaveService iLeaveService;

    @Autowired
    private LeaveMapper leaveMapper;

   /* @Test
    public void IUserServiceTest(){
        ServerResponse<User> login = iUserService.login("admin", "admin123");
        System.out.println(login.getMsg());
        System.out.println(login.getData());
    }*/

   @Test
   public void PageInfoTest(){
       ServerResponse<PageInfo> pageInfoServerResponse = iLeaveService.leaveList(1, 1);
       System.out.println(pageInfoServerResponse.getData());
   }

   @Test
   public void leaveSelectTest(){
       Leave leave = leaveMapper.selectByPrimaryKey(1);
       System.out.println(leave);
   }
}
