package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.service.domain.Rule;
import com.Yu.his.service.po.RuleQueryPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface RuleMapper extends CommonMapper<Rule> {

    List<HashMap> selectRuleListByPage(RuleQueryPo po);

    long selectRuleListCount(RuleQueryPo po);

}