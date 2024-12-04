package com.Yu.his.service.service;

import cn.hutool.core.bean.BeanUtil;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.domain.Rule;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.mapper.RuleMapper;
import com.Yu.his.service.po.RuleAddPo;
import com.Yu.his.service.po.RuleQueryPo;
import com.Yu.his.service.po.RuleUpdatePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/12 22:28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RuleService {
    final RuleMapper ruleMapper;
    public PageInfo searchRuleListByPage(RuleQueryPo po) {
        List<HashMap> list = new ArrayList<>();


        long l = ruleMapper.selectRuleListCount(po);
        if (l > 0L) {
            list = ruleMapper.selectRuleListByPage(po);
        }
        return new PageInfo(po.getPageIndex(), po.getPageSize(), (int) l, list);
    }

    public int insertRule(RuleAddPo po) {

        Rule rule = BeanUtil.copyProperties(po, Rule.class);
        int i = ruleMapper.insertSelective(rule);
        if (i != 1) {
            throw new HisException("新增规则失败");
        }
        return i;
    }

    public int updateRule(RuleUpdatePo po) {
        Rule rule = BeanUtil.copyProperties(po, Rule.class);
        int i = ruleMapper.updateByPrimaryKeySelective(rule);
        if (i != 1) {
            throw new HisException("修改规则失败");
        }

        return i;
    }
}
