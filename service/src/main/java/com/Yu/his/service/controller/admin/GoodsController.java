package com.Yu.his.service.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.minio.util.MinioUtil;
import com.Yu.his.service.converter.GoodsConverter;
import com.Yu.his.service.domain.GoodsWithBLOBs;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.po.*;
import com.Yu.his.service.service.GoodsService;
import com.Yu.his.service.vo.GoodInfoVo;
import com.Yu.his.service.vo.R;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/21 23:49
 */
@RestController
@Api(tags = "商品模块")
@Slf4j
@RequestMapping("/mis/goods")
@RequiredArgsConstructor
public class GoodsController {
    final GoodsService goodsService;
    @Resource
    private MinioUtil minioUtil;

    //    @GetMapping("/uploadCheckupExcel")
//    public R uploadImage(MultipartFile file) {
//        return R.ok();
//    }
    @GetMapping("/selectGoods")
    @ApiOperation(value = "查询商品分页信息")
    @SaCheckPermission(value = {"ROOT", "GOODS:SELECT"}, mode = SaMode.OR)
    public R selectGoodByPage(@Valid GoodsQueryPo po) {
        int userId = StpUtil.getLoginIdAsInt();
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(userId);
        log.info("" + tokenValueByLoginId);
        Integer pageIndex = po.getPageIndex();
        Integer pageSize = po.getPageSize();
        Integer start = (pageIndex - 1) * pageSize;
        po.setStart(start);
        PageInfo pageInfo = goodsService.selectByPage(po);
        return R.ok().put("page", pageInfo);
    }

    @PostMapping("/uploadImage")
    @SaCheckPermission(value = {"ROOT", "GOODS:INSERT", "GOODS:UPDATE"}, mode = SaMode.OR)
    public R uploadImage(@Param("file") MultipartFile file) throws IOException {
        String s = goodsService.uoloadImage(file);
        return R.ok().put("path", s);
    }


    @PostMapping("/addGoods")
    @SaCheckPermission(value = {"ROOT", "GOODS:INSERT"}, mode = SaMode.OR)
    public R addGoods(@RequestBody @Valid GoodsAddPo po) {
        GoodsWithBLOBs goodsEntity = GoodsConverter.INSTANCE.toGoodsEntity(po);
        String temp = "";
        if (po.getCheckup1() != null) {
            temp = JSONUtil.parseArray(po.getCheckup1()).toString();
            goodsEntity.setCheckup1(temp);
        }
        if (po.getCheckup2() != null) {
            temp = JSONUtil.parseArray(po.getCheckup2()).toString();
            goodsEntity.setCheckup2(temp);
        }
        if (po.getCheckup3() != null) {
            temp = JSONUtil.parseArray(po.getCheckup3()).toString();
            goodsEntity.setCheckup3(temp);
        }
        if (po.getCheckup4() != null) {
            temp = JSONUtil.parseArray(po.getCheckup4()).toString();
            goodsEntity.setCheckup4(temp);
        }
        if (po.getTag() != null) {
            temp = JSONUtil.parseArray(po.getTag()).toString();
            goodsEntity.setTag(temp);
        }
        int i = goodsService.insertGoods(goodsEntity);
        return R.ok().put("row", i);
    }

    @GetMapping("/searchGoodById")
    @SaCheckPermission(value = {"ROOT", "GOODS:SELECT"}, mode = SaMode.OR)
    @ApiOperation("查询商品信息")
    public R searchGoodById(SearchGoodByIdPo po) throws NoSuchFieldException, IllegalAccessException {
        GoodInfoVo goodInfoVo = goodsService.searchGoodById(po);
        return R.ok().put("result", goodInfoVo);
    }

    @PostMapping("/updateGoods")
    @SaCheckPermission(value = {"ROOT", "GOODS:UPDATE"}, mode = SaMode.OR)
    @ApiOperation("修改商品信息")
    public R updateGoods(@RequestBody @Valid GoodsUpdatePo po) {
        GoodsWithBLOBs goodsEntity = GoodsConverter.INSTANCE.toGoodsEntity1(po);
        String temp = "";
        if (po.getCheckup1() != null) {
            temp = JSONUtil.parseArray(po.getCheckup1()).toString();
            goodsEntity.setCheckup1(temp);
        }
        if (po.getCheckup2() != null) {
            temp = JSONUtil.parseArray(po.getCheckup2()).toString();
            goodsEntity.setCheckup2(temp);
        }
        if (po.getCheckup3() != null) {
            temp = JSONUtil.parseArray(po.getCheckup3()).toString();
            goodsEntity.setCheckup3(temp);
        }
        if (po.getCheckup4() != null) {
            temp = JSONUtil.parseArray(po.getCheckup4()).toString();
            goodsEntity.setCheckup4(temp);
        }
        if (po.getTag() != null) {
            temp = JSONUtil.parseArray(po.getTag()).toString();
            goodsEntity.setTag(temp);
        }
        int i = goodsService.updateGoods(goodsEntity);
        return R.ok().put("rows", i);
    }


    @DeleteMapping("/deleteGoodsById")
    @ApiOperation(value = "删除商品")
    @SaCheckPermission(value = {"ROOT", "GOODS:DELETE"}, mode = SaMode.OR)
    public R deleteGoodsBatch(@RequestParam(name = "ids") ArrayList<Integer> ids) {
        int i = goodsService.deleteGoodsBatch(ids);
        return R.ok().put("rows", i);
    }


    @PostMapping("/updateCheckupExcel")
    @SaCheckPermission(value = {"ROOT", "GOODS:UPDATE", "GOODS:INSERT"}, mode = SaMode.OR)
    @ApiOperation(value = "上传excel文档")
    public R updateCheckupExcel(@Valid UpdateCheckupPo po, @org.springframework.data.repository.query.Param("file") MultipartFile file) {
        goodsService.updateCheckupExcel(po.getId(), file);
        return R.ok();
    }

    @GetMapping("/downloadCheckupExcel")
    @SaCheckPermission(value = {"ROOT", "GOODS:UPDATE", "GOODS:INSERT", "GOODS:SELECT"}, mode = SaMode.OR)
    @ApiOperation(value = "下载excel文档")
    public R downloadCheckupExcel(@Valid DownloadCheckupPo po, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + po.getId() + ".xlsx");
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        String path = "/mis/goods/checkup/" + po.getId() + ".xlsx";
        try {
            InputStream inputStream = minioUtil.downloadExcel(path);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            IoUtil.copy(bis, bos);
            return R.ok();
        } catch (IOException e) {
            throw new HisException("文件下载失败");
        }
    }

    @GetMapping("searchSnapshotForMis")
    @ApiOperation(value = "查询商品快照")
    public R searchBySnapshotId(@Valid SearchSnapshotPo po) {
        String snapshotId = po.getSnapshotId();
        HashMap hashMap = goodsService.searchBySnapshotId(snapshotId);
        return R.ok().put("result", hashMap);
    }
}
