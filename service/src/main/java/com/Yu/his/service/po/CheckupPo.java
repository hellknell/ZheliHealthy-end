package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/28 19:24
 */
@Data
@ApiModel("体检内容实体")
public class CheckupPo implements Serializable {
    private static final long serialVersionUID = -434328973897L;


    @Length(max = 50, message = "体检项目名称超过50字符")
    private String title;

    @Length(max = 200, message = "体检内容名称超过200字符")
    private String content;
}
