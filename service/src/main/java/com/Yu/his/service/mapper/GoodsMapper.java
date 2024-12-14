package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.domain.Goods;
import com.Yu.his.service.domain.GoodsWithBLOBs;
import com.Yu.his.service.po.GoodsQueryPo;
import com.Yu.his.service.po.SearchGoodByIdPo;
import com.Yu.his.service.po.SearchGoodsListPo;
import com.Yu.his.service.vo.GoodInfoVo;
import com.Yu.his.service.vo.GoodsIndexVo;
import com.Yu.his.service.vo.GoodsListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper extends CommonMapper<Goods> {
    List<GoodsListVo> selectGoodList(GoodsQueryPo po);

    @Override
    GoodsWithBLOBs topOne(MyBatisWrapper example);

    /**
     * @param po
     * @return
     */
    long goodsCount(GoodsQueryPo po);

    GoodInfoVo selectById(SearchGoodByIdPo po);

    long searchGoodsListCount(SearchGoodsListPo po);

    List<GoodsIndexVo> selectGoodsListByPage(SearchGoodsListPo po);

    int updateSalesVolume(int id);
}