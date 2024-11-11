package com.Yu.his.service.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.Yu.his.service.service.user.AppointmentService;
import com.Yu.his.service.vo.AppointmentRecordVo;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/11 18:04
 */
@RestController
@Slf4j
@Api(tags = "预约模块")
@RequestMapping("/mis/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    final AppointmentService appointmentService;

    @GetMapping("/searchByOrder")
    @SaCheckPermission(value = {"ROOT", "APPOINTMENT:SELECT"}, mode = SaMode.OR)
    @ApiOperation(value = "根据订单号查询预约记录")
    public R searchByOrder(@RequestParam Integer orderId) {
        List<AppointmentRecordVo> list = appointmentService.searchByOrderId(orderId);
        return R.ok().put("result", list);
    }
}
