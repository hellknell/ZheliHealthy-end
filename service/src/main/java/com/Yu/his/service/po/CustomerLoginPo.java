package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/2 21:30
 */
@Data
public class CustomerLoginPo implements Serializable {
    private static final long serialVersionUID = -4343289737483897L;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[1-9]\\d{9}$", message = "手机号格式错误")
    private String tel;

    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    private String code;
}
