package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/10/31 20:00
 */
@Data
@ApiModel(value = "上传excel文档实体")
public class UpdateCheckupPo implements Serializable {
    private static final long serialVersionUID = -321554545343L;

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id无效")
    @ApiModelProperty(value = "商品id")
    private Integer id;

}
