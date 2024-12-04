package com.Yu.his.service.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.domain.Rule;
import com.Yu.his.service.domain.RuleField;
import com.Yu.his.service.mapper.RuleMapper;
import com.Yu.his.service.po.RuleAddPo;
import com.Yu.his.service.po.RuleQueryPo;
import com.Yu.his.service.po.RuleUpdatePo;
import com.Yu.his.service.service.RuleService;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    final RuleService ruleService;


    @GetMapping("/searchAllRules")
    @ApiOperation(value = "查询所有规则列表")
    public R searchAllRules() {
        MyBatisWrapper<Rule> wrapper = new MyBatisWrapper<>();
        wrapper.select(RuleField.Id, RuleField.Name, RuleField.Remark, RuleField.Rule);
        List<Rule> list = ruleMapper.list(wrapper);
        return R.ok().put("result", list);
    }

    @GetMapping("/searchRuleList")
    @ApiOperation(value = "查询规则列表")
    @SaCheckPermission(value = {"ROOT", "RULE:SELECT"}, mode = SaMode.OR)
    public R searchRuleList(@Valid RuleQueryPo po) {
        Integer pageIndex = po.getPageIndex();
        Integer start = (pageIndex - 1) * po.getPageSize();
        po.setStart(start);
        PageInfo pageInfo = ruleService.searchRuleListByPage(po);
        return R.ok().put("page", pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增规则列表")
    @SaCheckPermission(value = {"ROOT", "RULE:INSERT"}, mode = SaMode.OR)
    public R insertRule(@Valid @RequestBody RuleAddPo po) {
        int i = ruleService.insertRule(po);
        return R.ok().put("rows", i);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改规则列表")
    @SaCheckPermission(value = {"ROOT", "RULE:UPDATE"}, mode = SaMode.OR)
    public R updateRule(@Valid @RequestBody RuleUpdatePo po) {
        int i = ruleService.updateRule(po);
        return R.ok().put("rows", i);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除规则列表")
    @SaCheckPermission(value = {"ROOT", "RULE:UPDATE"}, mode = SaMode.OR)
    public R deleteRule(@PathVariable(value = "id") Integer id) {
        int i = ruleMapper.deleteByPrimaryKey(id);
        return R.ok().put("rows", i);
    }

}
