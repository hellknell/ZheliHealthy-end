package com.Yu.his.service.po;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/3 22:16
 */

@Data
public class SearchGoodsListPo extends PagePo implements Serializable {
    private static final long serialVersionUID = -2412887533L;

    private String keyword;
    @Pattern(regexp = "^男性$|^女性$", message = "性别格式有误")
    private String sex;
    private String type;
    @Range(min = 1, max = 4, message = "价格范围不正确")
    private Integer priceType;
    @Range(min = 1, max = 4, message = "排序范围不正确")
    private Integer orderType;
    private Integer start;

}
