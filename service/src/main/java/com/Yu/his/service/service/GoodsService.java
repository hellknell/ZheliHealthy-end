package com.Yu.his.service.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.minio.util.MinioUtil;
import com.Yu.his.service.domain.Goods;
import com.Yu.his.service.domain.GoodsField;
import com.Yu.his.service.domain.GoodsWithBLOBs;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.mapper.GoodsMapper;
import com.Yu.his.service.po.GoodsQueryPo;
import com.Yu.his.service.po.SearchGoodByIdPo;
import com.Yu.his.service.vo.GoodInfoVo;
import com.Yu.his.service.vo.GoodsListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/21 23:53
 */
@Service("MisGoodsService")
@RequiredArgsConstructor
@Slf4j
public class GoodsService {
    final TransactionTemplate template;
    final GoodsMapper goodsMapper;
    @Resource
    private MinioUtil minioUtil;

    public PageInfo selectByPage(GoodsQueryPo po) {
        List<GoodsListVo> goodsList = new ArrayList<>();
        long l = goodsMapper.goodsCount(po);
        if (l > 0L) {
            goodsList = goodsMapper.selectGoodList(po);
        }
        log.info("{}", l);
        return new PageInfo<>(po.getPageIndex(), po.getPageSize(), l > 0L ? (int) l : 0, goodsList);
    }


    public String uoloadImage(MultipartFile file) throws IOException {
        String fileName = IdUtil.simpleUUID() + ".jpg";
        String path = "front/goods/" + fileName;
        minioUtil.uploadImage(path, file);
        return path;
    }

    public int insertGoods(GoodsWithBLOBs goodsEntity) {
        String md5 = genMd5(goodsEntity);
        goodsEntity.setMd5(md5);
        DateTime now = DateTime.now();
        goodsEntity.setCreateTime(now);
        goodsEntity.setUpdateTime(now);
        goodsEntity.setStatus(Boolean.FALSE);
        goodsEntity.setSalesVolume(0);
        int i = goodsMapper.insertSelective(goodsEntity);
        return i;
    }

    private String genMd5(GoodsWithBLOBs po) {
        JSONObject json = JSONUtil.parseObj(po);
        json.remove("id");
        json.remove("partId");
        json.remove("salesVolume");
        json.remove("status");
        json.remove("md5");
        return MD5.create().digestHex(json.toString()).toUpperCase();
    }

    @CacheEvict(cacheNames = "goods", key = "#goods.id")
    public int updateGoods(GoodsWithBLOBs goods) {
        //更新
        String md5 = this.genMd5(goods);
        goods.setMd5(md5);
        int i;
        try {
            i = goodsMapper.updateByPrimaryKeySelective(goods);
            return i;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new HisException("修改商品失败");
        }
    }

    public GoodInfoVo searchGoodById(SearchGoodByIdPo po) throws NoSuchFieldException, IllegalAccessException {
        GoodInfoVo goodInfoVo = goodsMapper.selectById(po);
        Class<GoodInfoVo> clazz = GoodInfoVo.class;
        String[] columns = {"checkup1", "checkup2", "checkup3", "checkup3", "tag"};
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



    @CacheEvict(cacheNames = "goods", key = "#id")
    public void updateCheckupExcel(int id, MultipartFile file) {
        ArrayList list = new ArrayList<>();
        try (InputStream is = file.getInputStream(); BufferedInputStream bis = new BufferedInputStream(is)) {
            XSSFWorkbook sheets = new XSSFWorkbook(bis);
            XSSFSheet sheet = sheets.getSheetAt(0);

            for (int i = 1; i < sheet.getLastRowNum(); ++i) {
                XSSFRow row = sheet.getRow(i);
                String value_1 = row.getCell(0).getStringCellValue();
                String value_2 = row.getCell(1).getStringCellValue();
                String value_3 = row.getCell(2).getStringCellValue();
                String value_4 = row.getCell(3).getStringCellValue();
                String value_5 = row.getCell(4).getStringCellValue();
                String value_6 = row.getCell(5).getStringCellValue();
                String value_7 = row.getCell(6).getStringCellValue();
                String value_8 = row.getCell(7).getStringCellValue();
                LinkedHashMap map = new LinkedHashMap<>() {{

                    put("place", value_1);
                    put("name", value_2);
                    put("item", value_3);
                    put("type", value_4);
                    put("code", value_5);
                    put("sex", value_6);
                    put("value", value_7);
                    put("template", value_8);
                }};
                list.add(map);
            }
        } catch (Exception e) {
            throw new HisException("解析excel文件失败");
        }
        if (list.size() == 0) {
            throw new HisException("文档内容无效");
        }

        //TODO (何宇,2024/10/31,19:58) 存储到内容存储到 minio服务器
        String path = "/mis/goods/checkup/" + id + ".xlsx";
        minioUtil.updateExcel(path, file);
        //TODO (何宇,2024/10/31,20:26) 更新md5值
        MyBatisWrapper<GoodsWithBLOBs> wrapper = new MyBatisWrapper<>();
        wrapper.select(GoodsField.Md5, GoodsField.Id, GoodsField.Checkup, GoodsField.Checkup1, GoodsField.Checkup2, GoodsField.Checkup3, GoodsField.Checkup4, GoodsField.Code, GoodsField.CurrentPrice, GoodsField.Description, GoodsField.Image, GoodsField.InitialPrice, GoodsField.PartId, GoodsField.RuleId, GoodsField.SalesVolume, GoodsField.Status, GoodsField.Tag, GoodsField.Type, GoodsField.Title, GoodsField.UpdateTime, GoodsField.CreateTime).whereBuilder().andEq(GoodsField.setId(id));
        GoodsWithBLOBs goods = goodsMapper.topOne(wrapper);
        String md5 = this.genMd5(goods);
        goods.setMd5(md5);
        String string = JSONUtil.parseArray(list).toString();
        goods.setCheckup(string);
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        if (i != 1) {
            throw new HisException("更新商品记录失败");
        }
    }

    @CacheEvict(cacheNames = "goods", key = "#ids")
    @Transactional
    public int deleteGoodsBatch(ArrayList<? extends Number> ids) {
        MyBatisWrapper<GoodsWithBLOBs> wrapper = new MyBatisWrapper<>();
        wrapper.select(GoodsField.Image).whereBuilder().andIn(GoodsField.Id, ids);
        List<Goods> list = goodsMapper.list(wrapper);
        List<String> imagePaths = list.stream().map(Goods::getImage).collect(Collectors.toList());
        MyBatisWrapper<GoodsWithBLOBs> wrapper1 = new MyBatisWrapper<>();
        wrapper1.whereBuilder().andEq(GoodsField.setStatus(Boolean.FALSE)).andEq(GoodsField.setSalesVolume(0)).andIn(GoodsField.Id, ids);
        int rows = goodsMapper.deleteField(wrapper1);
        if (rows > 0) {
            imagePaths.forEach(path -> {
                try {
                    minioUtil.deleteFiles(path);
                } catch (Exception e) {
                    throw new HisException("文件删除失败");
                }
            });
        }
        return rows;

    }

}