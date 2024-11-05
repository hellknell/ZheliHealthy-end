package com.Yu.his.service.controller.user;

import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.po.SearchGoodByIdPo;
import com.Yu.his.service.po.SearchGoodsListPo;
import com.Yu.his.service.service.user.GoodsService;
import com.Yu.his.service.vo.GoodInfoVo;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/1 14:24
 */
@RestController("FrontGoodsController")
@RequestMapping("/front/goods")
@Api(tags = "用户端商品模块")
@RequiredArgsConstructor
public class GoodsController {
    final GoodsService goodsService;

    @GetMapping("/selectById")
    @ApiOperation("根据商品id查询商品")
    public R selectById(@Valid SearchGoodByIdPo po) throws NoSuchFieldException, IllegalAccessException {
        po.setStatus(Boolean.TRUE);
        GoodInfoVo goodInfoVo = goodsService.selectById(po);
        return R.ok().put("result", goodInfoVo);
    }

    @GetMapping("/searchIndexGoods")
    @ApiOperation(value = "查询首页商品")
    public R searchIndexGoodsLimit4(@RequestParam("partIds[]") ArrayList<Integer> partIds) {
//        Integer[] IntegerArray = Arrays.stream(po.getPartIds())
//                .boxed()
//                .toArray(Integer[]::new);
        HashMap map = goodsService.searchIndexGoodsLimit4(partIds);
        return R.ok().put("result", map);
    }

    @GetMapping("/searchGoodsList")
    @ApiOperation(value = "多条件查询商品")
    public R searchGoodsList(@Valid SearchGoodsListPo po) {
        Integer pageIndex = po.getPageIndex();
        pageIndex = (pageIndex - 1) * po.getPageSize();
        po.setStart(pageIndex);
        PageInfo pageInfo = goodsService.searchGoodsList(po);
        return R.ok().put("page", pageInfo);

    }

}
