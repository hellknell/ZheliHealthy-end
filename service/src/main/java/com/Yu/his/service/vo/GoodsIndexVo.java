package com.Yu.his.service.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/3 17:24
 */
@Data
@ApiModel("首页商品推荐实体")
public class GoodsIndexVo implements Serializable {
    private static final long serialVersionUID = -4343473542892L;
    private Integer id;
    private String code;
    private String title;
    private String description;
    private String image;
    private Integer initialPrice;
    private Integer currentPrice;
    private Integer salesVolume;
}
