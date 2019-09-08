package com.zss.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Leave {
    private Integer id;

    private String needTime;

    private String leaveTime;

    private String backTime;

    private Date createTime;

    private String status;

    private Integer uid;

    private String reason;
}