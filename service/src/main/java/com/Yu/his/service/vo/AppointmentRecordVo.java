package com.Yu.his.service.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/10 23:25
 */
@Data
public class AppointmentRecordVo implements Serializable {
    private Integer id;
    private String tel;
    private String name;
    private String sex;
    private Date date;
    private int age;
    private Byte status;
}