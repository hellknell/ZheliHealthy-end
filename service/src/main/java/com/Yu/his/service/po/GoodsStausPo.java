package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/29 22:54
 */
@ApiModel("修改商品状态实体")
@Data
public class GoodsStausPo implements Serializable {
    private static final long serialVersionUID = -43842934728L;
    @NotNull(message = "商品id不能为空")
    private Integer id;

    @NotNull(message = "状态不能为空")
    private Boolean status;
}
