package com.zss.vo;

public class WorkUserRoleVo {

    private String username;

    private String startWork;

    private String stopWork;

    private String createTime;

    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartWork() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        this.startWork = startWork;
    }

    public String getStopWork() {
        return stopWork;
    }

    public void setStopWork(String stopWork) {
        this.stopWork = stopWork;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public WorkUserRoleVo(String username, String startWork, String stopWork, String createTime, String role) {
        this.username = username;
        this.startWork = startWork;
        this.stopWork = stopWork;
        this.createTime = createTime;
        this.role = role;
    }

    public WorkUserRoleVo() {
        super();
    }
}
