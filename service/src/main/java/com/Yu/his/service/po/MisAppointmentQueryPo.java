package com.Yu.his.service.po;

import com.Yu.his.generator.help.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/15 16:41
 */
@Data
@ApiModel("后台查询预约记录实体")
public class MisAppointmentQueryPo extends PagePo implements Serializable {
    private String name;
    @Pattern(regexp = "^1[1-9]\\d{9}$", message = "手机号格式错误")
    private String tel;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String status;

    private Integer start;
}
