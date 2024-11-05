package com.Yu.his.service.service.user;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.converter.GoodsConverter;
import com.Yu.his.service.domain.Goods;
import com.Yu.his.service.domain.GoodsField;
import com.Yu.his.service.domain.GoodsWithBLOBs;
import com.Yu.his.service.mapper.GoodsMapper;
import com.Yu.his.service.po.SearchGoodByIdPo;
import com.Yu.his.service.po.SearchGoodsListPo;
import com.Yu.his.service.vo.GoodInfoVo;
import com.Yu.his.service.vo.GoodsIndexVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/1 14:26
 */
@Service("FrontGoodsServcie")
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    final GoodsMapper goodsMapper;

    @Cacheable(cacheNames = "goods", key = "#po.id")
    public GoodInfoVo selectById(SearchGoodByIdPo po) throws IllegalAccessException, NoSuchFieldException {
        GoodInfoVo goodInfoVo = goodsMapper.selectById(po);
        String[] columns = {"checkup1", "checkup2", "checkup3", "checkup3", "tag"};
        Class<GoodInfoVo> clazz = GoodInfoVo.class;
        String temp = null;
        for (String one : columns) {
            Field field = clazz.getDeclaredField(one);
            field.setAccessible(true);
            temp = (String) field.get(goodInfoVo);
            if (temp != null) {
                JSONArray objects = JSONUtil.parseArray(temp);
                field.set(goodInfoVo, StrUtil.removeAll(objects.toString(), '\\'));
            }
        }
        return goodInfoVo;
    }

    public HashMap<Object, List<GoodsIndexVo>> searchIndexGoodsLimit4(List<Integer> partIds) {
        HashMap<Object, List<GoodsIndexVo>> map = new HashMap<>();
        for (Integer partId : partIds) {
            MyBatisWrapper<GoodsWithBLOBs> wrapper = new MyBatisWrapper<>();
            wrapper.select(GoodsField.Image, GoodsField.Id, GoodsField.InitialPrice, GoodsField.CurrentPrice, GoodsField.SalesVolume, GoodsField.Code, GoodsField.Title, GoodsField.Description).whereBuilder().andEq(GoodsField.setPartId(partId.byteValue())).andEq(GoodsField.setStatus(Boolean.TRUE));
            List<Goods> list1 = goodsMapper.list(wrapper.orderByDesc(GoodsField.SalesVolume).limit(0, 4));
            List<GoodsIndexVo> collect = list1.stream().map(GoodsConverter.INSTANCE::toGoodsIndexVo).collect(Collectors.toList());
            map.put(partId, collect);
        }
        String jsonPrettyStr = JSONUtil.toJsonPrettyStr(map);
        log.info(jsonPrettyStr);
        return map;
    }

    public PageInfo searchGoodsList(SearchGoodsListPo po) {
        List<GoodsIndexVo> list = new ArrayList<>();
        long count = goodsMapper.searchGoodsListCount(po);
        if (count > 0L) {
            list = goodsMapper.selectGoodsListByPage(po);
        }
        return new PageInfo<>(po.getPageIndex(), po.getPageSize(), (int) count, list);
    }
}
