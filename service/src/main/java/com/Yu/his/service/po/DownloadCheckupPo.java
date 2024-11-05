package com.Yu.his.service.po;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/10/31 21:31
 */
@Data

public class DownloadCheckupPo implements Serializable {
    private static final long serialVersionUID = -423853958348L;
    @NotNull(message = "id不能为空")
    private Integer id;
}
