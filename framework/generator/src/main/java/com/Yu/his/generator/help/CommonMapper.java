package com.Yu.his.generator.help;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T row);

    int insertSelective(T row);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T row);

    int updateByPrimaryKey(T row);

    List<T> list(MyBatisWrapper example);

    T topOne(MyBatisWrapper example);

    Integer count(MyBatisWrapper example);

    int updateField(@Param("example") MyBatisWrapper example);

    int updateFieldBatch(List<MyBatisWrapper> list);

    int insertBatch(@Param("list") List<T> list);

    T get(MyBatisWrapper example);

    int updateBatchSelective(@Param("list") List<T> list);

    int deleteByExample(@Param("example") MyBatisWrapper example);

    int deleteField(@Param("example") MyBatisWrapper example);
}