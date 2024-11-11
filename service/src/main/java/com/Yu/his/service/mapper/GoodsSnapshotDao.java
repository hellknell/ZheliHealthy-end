package com.Yu.his.service.mapper;

import com.Yu.his.service.domain.GoodsSnapshotEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 17:09
 */
@Repository
public class GoodsSnapshotDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public String hasGoodsSnapshot(String md5) {
        Criteria md51 = new Criteria("md5").is(md5);
        Query query = new Query(md51);
        query.skip(0);
        query.limit(1);
        GoodsSnapshotEntity goodsSnapshotEntity = mongoTemplate.findOne(query, GoodsSnapshotEntity.class);
        return goodsSnapshotEntity != null ? goodsSnapshotEntity.get_id() : null;
    }

    public String insert(GoodsSnapshotEntity entity) {
        return mongoTemplate.save(entity).get_id();
    }


    public GoodsSnapshotEntity searchBySnapshotId(String id) {
        GoodsSnapshotEntity byId = mongoTemplate.findById(id, GoodsSnapshotEntity.class);
        return byId;
    }
}
