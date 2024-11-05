package com.Yu.his.service.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/3 18:11
 */
@Data
public class SearchGoodsIndexPo implements Serializable {
    private static final long serialVersionUID = -2412887534823L;
    private List<Integer> partIds;
}
