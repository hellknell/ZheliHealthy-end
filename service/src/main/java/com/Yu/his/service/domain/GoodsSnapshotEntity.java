package com.Yu.his.service.domain;

import com.Yu.his.service.po.CheckupPo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 16:40
 */
@Data
@Document("goods_snapshot")
public class GoodsSnapshotEntity implements Serializable {
    @Id
    private String _id;
    @Indexed
    private Integer id;
    private String title;

    private String code;
    private String description;
    private List<CheckupPo> checkup_1;
    private List<CheckupPo> checkup_2;
    private List<CheckupPo> checkup_3;
    private List<CheckupPo> checkup_4;
    private String image;
    private BigDecimal initialPrice;
    private BigDecimal currentPrice;
    private List<Map> checkup;
    private String type;
    private List<String> tag;
    private String ruleName;
    private String rule;

    @Indexed
    private String md5;


}
