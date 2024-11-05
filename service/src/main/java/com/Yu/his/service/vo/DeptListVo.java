package com.Yu.his.service.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 18:55
 */
@Data
@ApiModel("部门返回列表")
public class DeptListVo implements Serializable {
    private static final long serialVersionUID = 43243858932L;
    private String deptName;
    private Integer id;
}
