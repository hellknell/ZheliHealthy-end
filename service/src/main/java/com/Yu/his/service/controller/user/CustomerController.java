package com.Yu.his.service.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.IdUtil;
import com.Yu.his.minio.util.MinioUtil;
import com.Yu.his.service.config.StpCustomerUtil;
import com.Yu.his.service.po.CustomerLoginPo;
import com.Yu.his.service.po.CustomerUpdatePo;
import com.Yu.his.service.po.SmsSendCodePo;
import com.Yu.his.service.service.user.CustomerService;
import com.Yu.his.service.vo.OrderStatisticVo;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/2 17:59
 */
@RestController
@Api(tags = "客户模块")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/front/customer")
public class CustomerController {
    final CustomerService customerService;
    @Resource
    private MinioUtil minioUtil;

    @GetMapping("/sendSmsCode")
    @ApiOperation(value = "发送验证码")
    public R sendSmsCode(@Valid SmsSendCodePo po) {
        Boolean b = customerService.sendSmsCode(po.getTel());
        String msg = b ? "短信验证码已发送" : "无法发送验证码";
        return R.ok(msg).put("result", b);

    }

    @PostMapping("/login")
    @ApiOperation(value = "客户登录")
    public R login(@RequestBody @Valid CustomerLoginPo po) {
        int id = customerService.login(po.getTel(), po.getCode());
        StpCustomerUtil.stpLogic.login(id, "PC");
        String token = StpCustomerUtil.stpLogic.getTokenValueByLoginId(id);
        return R.ok().put("token", token).put("id", id);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "客户登出")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    public R logout() {
        StpCustomerUtil.stpLogic.logout("PC");
        return R.ok();
    }

    @GetMapping("/checkLogin")
    @ApiOperation(value = "验证登录状态")
    public R checkLogin() {
        boolean flag = StpCustomerUtil.stpLogic.isLogin();
        return R.ok().put("result", flag);
    }

    @GetMapping("/searchSummary")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    @ApiOperation(value = "查询客户摘要信息")
    public R searchSummary() {
        int loginId = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        OrderStatisticVo orderStatisticVo = customerService.searchSummary(loginId);
        return R.ok().put("result", orderStatisticVo);
    }

    @PostMapping("/update")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    @ApiOperation(value = "更新客户信息")
    public R update(@RequestBody @Valid CustomerUpdatePo po) {
        int loginId = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        po.setId(loginId);
        int update = customerService.update(po);
        return R.ok().put("rows", update);
    }

    @PostMapping("/uploadImage")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    @ApiOperation(value = "更新用户头像")
    public R updatePhoto(@Param("file") MultipartFile file) throws IOException {
        String fileName = IdUtil.simpleUUID().toUpperCase() + ".jpg";
        String path = "front/customer/" + fileName;
        String s = minioUtil.uploadImage(path, file);
        return R.ok().put("result", s);
    }


}
