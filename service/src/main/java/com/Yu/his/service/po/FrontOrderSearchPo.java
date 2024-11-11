package com.Yu.his.service.po;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/7 22:54
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel("查询订单实体")
@Data
public class FrontOrderSearchPo extends PagePo implements Serializable {
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,50}$", message = "keyword内容不正确")
    private String keyword;
    @Pattern(regexp = "^1$|^3$", message = "status不正确")
    private String status;
    private Integer start;
    private Integer customerId;
}
