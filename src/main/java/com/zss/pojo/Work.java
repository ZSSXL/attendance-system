package com.zss.pojo;

import java.util.Date;

public class Work {
    private Integer id;

    private String startWork;

    private String stopWork;

    private Date createTime;

    private Integer uid;

    public Work(Integer id, String startWork, String stopWork, Date createTime, Integer uid) {
        this.id = id;
        this.startWork = startWork;
        this.stopWork = stopWork;
        this.createTime = createTime;
        this.uid = uid;
    }

    public Work() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", startWork=" + startWork +
                ", stopWork=" + stopWork +
                ", createTime=" + createTime +
                ", uid=" + uid +
                '}';
    }
}