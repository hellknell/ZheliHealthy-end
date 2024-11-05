package com.Yu.his.service.controller.admin;

import com.Yu.his.service.service.RoleService;
import com.Yu.his.service.vo.R;
import com.Yu.his.service.vo.RoleListVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 19:18
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "角色模块")
@RequestMapping("/mis/role")
public class RoleController {
    final RoleService roleService;

    @GetMapping("/searchAllRole")
    public R selectAlRoles() {
        List<RoleListVo> roleListVos = roleService.selectAllRole();
        return R.ok().put("list", roleListVos);
    }


}
