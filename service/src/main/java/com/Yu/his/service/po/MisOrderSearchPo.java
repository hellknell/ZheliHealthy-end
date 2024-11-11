package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/9 22:22
 */
@Data
@ApiModel("mis端订单查询参数实体")
public class MisOrderSearchPo extends PagePo implements Serializable {
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,30}$", message = "订单名称有误")
    private String keyword;
    private String code;
    private String status;
    private String tel;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date startDate;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date endDate;
    private Integer start;
}
