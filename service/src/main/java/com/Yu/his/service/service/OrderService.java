package com.Yu.his.service.service;

import cn.hutool.core.util.ByteUtil;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.domain.Order;
import com.Yu.his.service.domain.OrderField;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.mapper.OrderMapper;
import com.Yu.his.service.po.MisOrderSearchPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/9 22:28
 */
@Slf4j
@Service(value = "MisOrderService")
@RequiredArgsConstructor
public class OrderService {
    final OrderMapper orderMapper;
    public PageInfo searchByPage(MisOrderSearchPo po) {
        List<HashMap> objects = new ArrayList<>();
        long l = orderMapper.searchCount(po);
        if (l > 0L) {
            objects = orderMapper.searchByPage(po);
        }
        return new PageInfo(po.getPageIndex(), po.getPageSize(), (int) l, objects);
    }

    public int deleteOrderById(int id) {
        MyBatisWrapper<Order> wrapper = new MyBatisWrapper<>();
        wrapper.whereBuilder().andEq(OrderField.setStatus(ByteUtil.intToByte(2))).andEq(OrderField.setId(id));
        int i = orderMapper.deleteField(wrapper);
        if (i != 1) {
            throw new HisException("删除订单失败");
        }
        return i;
    }

    public int updateRefundStatus(Integer id) {
        MyBatisWrapper<Order> wrapper = new MyBatisWrapper<>();
        wrapper.update(OrderField.setStatus(ByteUtil.intToByte(4)))
                .whereBuilder().andEq(OrderField.setId(id)).andEq(OrderField.setStatus(ByteUtil.intToByte(3)));
        int i = orderMapper.updateField(wrapper);
        if (1 != i) {
            throw new HisException("修改订单状态失败");
        }
        return i;
    }


    public boolean checkOrderIfValid(int customerId, Integer orderId) {
        MyBatisWrapper<Order> wrapper = new MyBatisWrapper<>();
        wrapper.select(OrderField.Id).whereBuilder().
                andEq(OrderField.setId(orderId)).andEq(OrderField.setCustomerId(customerId));
        Order order = orderMapper.topOne(wrapper);
        return order != null;
    }
}
