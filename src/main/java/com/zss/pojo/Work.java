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
public class Work {
    private Integer id;

    private String startWork;

    private String stopWork;

    private Date createTime;

    private Integer uid;
}