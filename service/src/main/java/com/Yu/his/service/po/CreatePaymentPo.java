package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 17:41
 */
@Data
public class CreatePaymentPo {
    @NotNull(message = "商品id不能为空")
    @Min(value = 1, message = "商品id不能小于1")
    private Integer goodsId;
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量不能小于1")
    private Integer number;

    private Integer customerId;
}
