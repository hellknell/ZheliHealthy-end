package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.service.domain.Order;
import com.Yu.his.service.po.FrontOrderSearchPo;
import com.Yu.his.service.po.MisOrderSearchPo;
import com.Yu.his.service.vo.OrderStatisticVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends CommonMapper<Order> {
    OrderStatisticVo searchOrderStatistic(int id);

    boolean selectIllegalOrder(int id);

    HashMap searchGoodsSnapshotById(int id);

    int closeTimeoutOrder();

    boolean updatePayment(Map map);

    List<HashMap> selectFrontOrderByPage(FrontOrderSearchPo po);

    long selectFrontOrderCount(FrontOrderSearchPo po);

    List<HashMap> searchByPage(MisOrderSearchPo po);

    long searchCount(MisOrderSearchPo po);
}