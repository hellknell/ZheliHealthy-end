package com.Yu.his.service.controller.user;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.config.StpCustomerUtil;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.po.CreateAppointmentPo;
import com.Yu.his.service.po.FrontAppointmentSearchPo;
import com.Yu.his.service.service.OrderService;
import com.Yu.his.service.service.user.AppointmentService;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/14 12:11
 */
@RestController(value = "FrontAppointmentController")
@Slf4j
@Api(tags = "用户预约模块")
@RequestMapping("/front/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    final OrderService orderService;
    final AppointmentService appointmentService;

    @PostMapping("/insert")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    @ApiOperation(value = "用户预约")
    public R createAppointment(@RequestBody @Valid CreateAppointmentPo po) {
        //TODO (何宇,2024/11/14,9:45) 判断预约是否有效
        int customerId = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        boolean b = orderService.checkOrderIfValid(customerId, po.getOrderId());
        if (!b) {
            throw new HisException("查询不到订单记录,预约失败");
        }
        //TODO (何宇,2024/11/14,9:45) 插入预约记录
        String pid = po.getPid();
        if (!IdcardUtil.isValidCard(pid)) {
            throw new HisException("身份证号无效");
        }
        String sex = IdcardUtil.getGenderByIdCard(pid) == 1 ? "男" : "女";
        DateTime birthDate = IdcardUtil.getBirthDate(pid);
        Date date = po.getDate();
        DateTime now = new DateTime(date);
        DateTime tomorrow = DateUtil.tomorrow();
        DateTime startDate = DateUtil.parse(tomorrow.toDateStr());
        DateTime endDate = tomorrow.offset(DateField.DAY_OF_MONTH, 60);
        boolean in = now.isIn(startDate, endDate);
        if (!in) {
            throw new HisException("日期须在60天之内");
        }
        po.setBirthday(birthDate.toJdkDate());
        po.setSex(sex);
        String s = appointmentService.insertAppointment(po);
        return R.ok().put("result", s);
    }


    @GetMapping("/searchByPage")
    @ApiOperation(value = "用户查询预约记录")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    public R searchByPage(FrontAppointmentSearchPo po) {

        int loginIdAsInt = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        Integer pageIndex = po.getPageIndex();
        Integer start = (pageIndex - 1) * po.getPageSize();
        po.setStart(start);
        po.setCustomerId(loginIdAsInt);
        PageInfo pageInfo = appointmentService.searchAppointmentByPage(po);
        return R.ok().put("page", pageInfo);
    }
}
