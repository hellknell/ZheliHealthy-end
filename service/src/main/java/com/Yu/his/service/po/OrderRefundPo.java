package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/8 14:17
 */
@Data
@ApiModel("订单退款实体")
public class OrderRefundPo implements Serializable {
    private Integer customerId;
    @NotNull(message = "id不能为空")
    private Integer id;
}
