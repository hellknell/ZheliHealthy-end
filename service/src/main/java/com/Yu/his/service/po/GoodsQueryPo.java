package com.Yu.his.service.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/26 23:50
 */
@Data
@NoArgsConstructor
@ApiModel("商品查询参数实体")
public class GoodsQueryPo extends PagePo implements Serializable {
    private static final long serialVersionUID = 4382934728L;
    @ApiModelProperty(value = "关键字")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5]{1,50}$")
    private String keyword;

    @ApiModelProperty(value = "商品编码")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,20}$")
    private String code;

    @ApiModelProperty(value = "商品类型")
//    @Pattern(regexp = "父母体检 | 入职体检 | 入学体检 | 中青年体检 | 个人高端 | 职场白领", message = "商品类型错误")
    private String type;

    @ApiModelProperty(value = "商品分区")
    @Range(min = 1, max = 4, message = "分区id无效")
    private Integer partId;
    private Boolean status;
    private Integer start;

}
