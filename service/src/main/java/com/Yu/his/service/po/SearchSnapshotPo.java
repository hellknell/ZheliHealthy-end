package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/11 18:52
 */
@Data
public class SearchSnapshotPo implements Serializable {
    @Pattern(regexp = "^[a-z0-9]{24}$", message = "snapshotId格式有误")
    @NotBlank(message = "snapshotId不能为空")
    private String snapshotId;
}
