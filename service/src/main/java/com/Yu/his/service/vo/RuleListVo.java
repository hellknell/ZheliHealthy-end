package com.Yu.his.service.vo;

import com.Yu.his.service.domain.GoodsWithBLOBs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/11 22:55
 */
@Data
public class RuleListVo implements Serializable {

    private Integer id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 价格计算规则
     */
    private String rule;

    private List<GoodsWithBLOBs> goods;

}
