package com.zss.vo;

import java.util.Date;

public class LeaveUserRoleVo {

    private Integer id;

    private String needTime;

    private String leaveTime;

    private String backTime;

    private String createTime;

    private String status;

    private String reason;

    private String role;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNeedTime() {
        return needTime;
    }

    public void setNeedTime(String needTime) {
        this.needTime = needTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LeaveUserRoleVo{" +
                "id=" + id +
                ", needTime='" + needTime + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", backTime='" + backTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", role='" + role + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public LeaveUserRoleVo() {
        super();
    }

    public LeaveUserRoleVo(Integer id, String needTime, String leaveTime, String backTime, String createTime, String status, String reason, String role, String username) {
        this.id = id;
        this.needTime = needTime;
        this.leaveTime = leaveTime;
        this.backTime = backTime;
        this.createTime = createTime;
        this.status = status;
        this.reason = reason;
        this.role = role;
        this.username = username;
    }
}
