package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/11 19:53
 */
@Data
public class DeleteOrderPo implements Serializable {

    @NotNull(message = "id不能为空")
    private Integer id;

}
