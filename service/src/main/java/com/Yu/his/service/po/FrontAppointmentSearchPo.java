package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/14 23:19
 */
@Data
@ApiModel("用户预约记录查询实体")
public class FrontAppointmentSearchPo extends PagePo implements Serializable {
    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String status;
    private Integer start;
    private Integer customerId;
}
