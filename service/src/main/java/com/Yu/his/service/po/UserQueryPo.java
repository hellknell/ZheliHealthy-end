package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 14:39
 */
@Data
@ApiModel(value = "查询用户实体参数")
public class UserQueryPo extends PagePo implements Serializable {
    private static final long serialVersionUID = -231223L;

    @ApiModelProperty(value = "姓名")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,20}$", message = "用户名格式有误")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("部门id")
    private Integer deptId;

    @ApiModelProperty("用户状态")
    private Integer status;
    private Integer start;
}
