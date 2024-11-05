package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/28 18:56
 */
@Data
@ApiModel("修改商品实体参数")
public class GoodsUpdatePo implements Serializable {
    private static final long serialVersionUID = -423854958348L;

    private Integer id;
    private String code;
    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品封面
     */

    private String image;
    /**
     * 原价
     */
    private BigDecimal initialPrice;

    /**
     * 现价
     */
    private BigDecimal currentPrice;

    /**
     * 套餐类型
     */
    private String type;

    /**
     * 1活动专区，2热卖套餐，3新品推荐，4孝敬父母，5,白领精英
     */
    private Byte partId;

    /**
     * 促销优惠规则的ID
     */
    private Integer ruleId;


    /**
     * MD5信息
     */
    private String md5;

    @Valid
    private ArrayList<CheckupPo> checkup1;

    /**
     * 实验室检查
     */
    @Valid
    private ArrayList<CheckupPo> checkup2;

    /**
     * 医技检查
     */
    @Valid
    private ArrayList<CheckupPo> checkup3;
    /**
     * 其他检查
     */
    @Valid
    private ArrayList<CheckupPo> checkup4;
    /**
     * 套餐标签
     */
    private String[] tag;
    private Boolean status;
}
