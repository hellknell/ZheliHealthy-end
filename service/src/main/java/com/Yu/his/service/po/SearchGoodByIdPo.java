package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/30 20:48
 */
@Data
public class SearchGoodByIdPo implements Serializable {
    private static final long serialVersionUID = -241288753482L;

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Integer id;

    private Boolean status;
}
