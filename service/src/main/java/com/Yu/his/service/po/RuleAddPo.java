package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/13 16:09
 */
@Data
public class RuleAddPo implements Serializable {
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}$")
    @NotBlank(message = "规则名称不能为空")
    private String name;

    @NotBlank(message = "规则不能为空")
    private String rule;

    private String remark;
}
