package com.Yu.his.service.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.po.DeleteAppointmentPo;
import com.Yu.his.service.po.MisAppointmentQueryPo;
import com.Yu.his.service.service.AppointmentService;
import com.Yu.his.service.service.OrderService;
import com.Yu.his.service.vo.AppointmentRecordVo;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    final OrderService orderService;
    @GetMapping("/searchByOrder")
    @SaCheckPermission(value = {"ROOT", "APPOINTMENT:SELECT"}, mode = SaMode.OR)
    @ApiOperation(value = "根据订单号查询预约记录")
    public R searchByOrder(@RequestParam Integer orderId) {
        List<AppointmentRecordVo> list = appointmentService.searchByOrderId(orderId);
        return R.ok().put("result", list);
    }

    @GetMapping("/searchByPageForMis")
    @SaCheckPermission(value = {"ROOT", "APPOINTMENT:SELECT"}, mode = SaMode.OR)
    @ApiOperation(value = "查询用户预约记录")
    public R searchByPageForMis(MisAppointmentQueryPo po) {
        Integer pageIndex = po.getPageIndex();
        Integer start = (pageIndex-1) * po.getPageSize();
        po.setStart(start);
        PageInfo pageInfo = appointmentService.searchByPage(po);
        return R.ok().put("page", pageInfo);
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = {"ROOT", "APPOINTMENT:DELETE"}, mode = SaMode.OR)
    @ApiOperation(value = "删除预约记录")
    public R delete(@RequestBody DeleteAppointmentPo po) {
        int rows = appointmentService.delete(po.getIds());
        return R.ok().put("rows", rows);
    }
}
