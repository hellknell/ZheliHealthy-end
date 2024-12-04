package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/15 17:17
 */
@Data
@ApiModel("后台删除预约记录实体")
public class DeleteAppointmentPo implements Serializable {
    private Integer[] ids;
}
