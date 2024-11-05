package com.Yu.his.service.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 19:20
 */
@Data
@ApiModel("角色返回列表")
public class RoleListVo implements Serializable {
    private static final long serialVersionUID = -74382493243L;
    private String roleName;
    private Integer id;
}
