package com.Yu.his.service.po;

import lombok.Data;

import java.io.Serializable;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/16 12:05
 取消 */
@Data

public class OrderCancelPo implements Serializable {
    private Integer customerId;
    private Integer id;
}

