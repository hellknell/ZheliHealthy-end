package com.Yu.his.service.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/21 23:54
 */
@Data
public class GoodsListVo implements Serializable {
    private static final long serialVersionUID = -43432L;

    private Integer id;
    /**
     * 商品名称
     */
    private String title;
    private String code;
    /**
     * 类型
     */
    private String type;
    /**
     * 现价
     */
    private Integer currentPrice;
    /**
     * 原价
     */
    private Integer initialPrice;

    private String ruleName;
    /**
     * 销量
     */
    private String salesVolume;
    private Boolean status;
    private Boolean hasCheckup;
    private String description;
}
