package com.Yu.his.service.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/30 20:45
 */
@Data
public class GoodInfoVo implements Serializable {
    private static final long serialVersionUID = -43434732892L;
    private String code;
    private String title;
    private String description;
    private String image;
    private String initialPrice;
    private String currentPrice;
    private Integer ruleId;
    private String ruleName;
    private String type;
    private String tag;
    private Integer partId;
    private String checkup1;
    private String checkup2;
    private String checkup3;
    private String checkup4;
    private Integer count1;
    private Integer count2;
    private Integer count3;
    private Integer count4;

}
