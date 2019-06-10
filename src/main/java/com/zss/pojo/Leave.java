package com.zss.pojo;

import java.util.Date;

public class Leave {
    private Integer id;

    private String needTime;

    private String leaveTime;

    private String backTime;

    private Date createTime;

    private String status;

    private Integer uid;

    private String reason;

    public Leave(Integer id, String needTime, String leaveTime, String backTime, Date createTime, String status, Integer uid, String reason) {
        this.id = id;
        this.needTime = needTime;
        this.leaveTime = leaveTime;
        this.backTime = backTime;
        this.createTime = createTime;
        this.status = status;
        this.uid = uid;
        this.reason = reason;
    }

    public Leave() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", needTime='" + needTime + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", backTime='" + backTime + '\'' +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                ", uid=" + uid +
                ", reason='" + reason + '\'' +
                '}';
    }
}