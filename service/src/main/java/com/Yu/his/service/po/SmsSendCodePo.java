package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/2 17:52
 */
@Data
public class SmsSendCodePo implements Serializable {
    private static final long serialVersionUID = -24128875348238L;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[1-9]\\d{9}$",message ="手机号格式错误")
    private String tel;
}
