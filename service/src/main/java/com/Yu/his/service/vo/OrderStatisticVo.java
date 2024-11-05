package com.Yu.his.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/3 10:00
 */
@Data
public class OrderStatisticVo implements Serializable {
    private static final long serialVersionUID = -4343342892L;
    /**
     *订单数量
     */
    private Integer count;
    /**
     *套餐数量
     */
    private Integer number;
    /**
     *总金额
     */
    private Integer amount;
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String tel;

    /**
     * 照片URL
     */
    private String photo;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date createTime;


}
