package com.Yu.his.service.controller.admin;

import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.domain.Rule;
import com.Yu.his.service.domain.RuleField;
import com.Yu.his.service.mapper.RuleMapper;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/28 20:39
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "促销规则模块")
@RequestMapping("mis/rule")
public class RuleController {
    final RuleMapper ruleMapper;

    @GetMapping("/searchAllRules")
    @ApiOperation(value = "查询所有规则列表")
    public R searchAllRules() {
        MyBatisWrapper<Rule> wrapper = new MyBatisWrapper<>();
        wrapper.select(RuleField.Id, RuleField.Name);
        List<Rule> list = ruleMapper.list(wrapper);
        return R.ok().put("result", list);
    }
}
