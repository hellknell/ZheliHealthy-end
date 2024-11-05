package com.Yu.his.service.converter;

import com.Yu.his.service.domain.Goods;
import com.Yu.his.service.domain.GoodsWithBLOBs;
import com.Yu.his.service.po.GoodsAddPo;
import com.Yu.his.service.po.GoodsUpdatePo;
import com.Yu.his.service.vo.GoodsIndexVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GoodsConverter {

    GoodsConverter INSTANCE = Mappers.getMapper(GoodsConverter.class);

    @Mappings({@Mapping(source = "checkup1", target = "checkup1", ignore = true), @Mapping(source = "checkup2", target = "checkup2", ignore = true), @Mapping(source = "checkup3", target = "checkup3", ignore = true), @Mapping(source = "checkup4", target = "checkup4", ignore = true), @Mapping(source = "tag", target = "tag", ignore = true)})
    GoodsWithBLOBs toGoodsEntity(GoodsAddPo po);

    @Mappings({@Mapping(source = "checkup1", target = "checkup1", ignore = true), @Mapping(source = "checkup2", target = "checkup2", ignore = true), @Mapping(source = "checkup3", target = "checkup3", ignore = true), @Mapping(source = "checkup4", target = "checkup4", ignore = true), @Mapping(source = "tag", target = "tag", ignore = true)})
    GoodsWithBLOBs toGoodsEntity1(GoodsUpdatePo po);

    GoodsIndexVo toGoodsIndexVo(Goods goods);

}
