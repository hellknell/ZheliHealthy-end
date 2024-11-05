package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/3 10:48
 */
@Data
public class CustomerUpdatePo implements Serializable {
    private static final long serialVersionUID = -43432897385344597L;
    private Integer id;
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,20}$", message = "姓名格式有误")
    private String name;

    @Pattern(regexp = "^男$|^女$", message = "sex内容不正确")
    private String sex;
    @Pattern(regexp = "^1[1-9]\\d{9}$", message = "tel格式有误")
    private String tel;

}
