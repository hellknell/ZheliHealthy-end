package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 14:34
 */
@Data
@ApiModel(value = "分页参数")
public class PagePo implements Serializable {
    private static final long serialVersionUID = -32154354545343L;


    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不能为空")
    private Integer pageIndex = 1;


    @ApiModelProperty(value = "页码")
    @Range(min = 1, max = 50, message = "页数超出范围")
    @NotNull(message = "页数不能为空")
    private Integer pageSize = 10;
}
