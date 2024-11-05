package com.Yu.his.service.controller.admin;

import com.Yu.his.service.service.DeptService;
import com.Yu.his.service.vo.DeptListVo;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 18:52
 */
@RestController
@RequestMapping("/mis/dept")
@RequiredArgsConstructor
@Api(tags = "部门模块")
public class DeptController {
    final DeptService deptService;
    @GetMapping("/searchAllDept")
    public R searchAllDept() {
        List<DeptListVo> list = deptService.selectAllDept();
        return R.ok().put("list", list);
    }

}
