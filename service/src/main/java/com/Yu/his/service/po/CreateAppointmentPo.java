package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/14 9:45
 */
@Data
@ApiModel("创建预约记录实体")
public class CreateAppointmentPo implements Serializable {
    /**
     *订单号
     */
    private Integer orderId;
    /**
     * 预约日期
     */
    private Date date;
    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "姓名格式不正确")
    private String name;
    /**
     * 身份证
     */
    private String pid;
    /**
     *电话号码
     */
    private String tel;
    /**
     * 邮寄地址
     */
    private String mailingAddress;
    /**
     *公司
     */
    private String company;
    private Date birthday;
    private String sex;
}
