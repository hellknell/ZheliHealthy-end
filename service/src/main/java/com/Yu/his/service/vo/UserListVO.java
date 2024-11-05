package com.Yu.his.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 15:03
 */
@Data
public class UserListVO implements Serializable {
    private static final long serialVersionUID = -74382493249L;

    private Integer id;
    private String name;
    private String tel;
    private String email;
    private String sex;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    private String roles;
    private String root;
    private String dept;
}
