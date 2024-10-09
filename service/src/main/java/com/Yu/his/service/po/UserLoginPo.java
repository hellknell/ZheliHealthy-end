package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/6 12:50
 */
@ApiModel("用户模块")
@Data
public class UserLoginPo implements Serializable {
    private static final long serialVersionUID = 32132L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,20}$",message = "用户名格式有误")
    private String userName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "密码格式有误")
    private String passWord;

}
