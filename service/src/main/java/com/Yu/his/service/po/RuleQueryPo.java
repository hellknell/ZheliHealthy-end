package com.Yu.his.service.po;

import lombok.Data;
import org.bouncycastle.asn1.cms.PasswordRecipientInfo;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/12 22:18
 */
@Data
public class RuleQueryPo extends PagePo implements Serializable {
    @Pattern(regexp ="^[a-zA-Z0-9\\u4e00-\\u4fa5]{1,30}$" ,message = "name字段不正确")
    private String name;


    private Integer start;
}
