package com.Yu.his.service.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/9 17:17
 */
@Data
@Api(tags = "修改密码参数实体")
public class UpdatePassPo implements Serializable {
    private static final long serialVersionUID = -3215435343L;
    @NotBlank(message = "原密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$")
    private String password;


    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$")
    private String newPassword;

    private int userId;

}
