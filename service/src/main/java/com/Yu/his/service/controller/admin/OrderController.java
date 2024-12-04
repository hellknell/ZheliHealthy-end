package com.Yu.his.service.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.po.DeleteOrderPo;
import com.Yu.his.service.po.MisOrderSearchPo;
import com.Yu.his.service.service.OrderService;
import com.Yu.his.service.vo.R;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/9 22:32
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mis/order")
public class OrderController {
    final OrderService orderService;
    @GetMapping("search")
    @SaCheckPermission(value = {"ROOT", "ORDER:SELECT"}, mode = SaMode.OR)
    public R searchByPage(@Valid MisOrderSearchPo po) {
        if ((po.getStartDate() != null && po.getEndDate() == null) || (po.getEndDate() != null && po.getStartDate() == null)) {
            throw new HisException("不允许开始日期和结束日期有一个为空");
        } else if (po.getStartDate() != null && po.getEndDate() != null) {
            boolean before = po.getEndDate().before(po.getStartDate());
            if (before) {
                throw new HisException("结束日期不得早于开始日期");
            }
        }
        Integer pageIndex = po.getPageIndex();
        Integer start = (pageIndex - 1) * po.getPageSize();
        po.setStart(start);
        PageInfo pageInfo = orderService.searchByPage(po);
        return R.ok().put("page", pageInfo);
    }
    @DeleteMapping("deleteOrder")
    public R deleteOrderById(@Valid DeleteOrderPo po) {
        Integer orderId = po.getId();
        int rows = orderService.deleteOrderById(orderId);
        return R.ok().put("rows", rows);
    }
    @PostMapping("/updateRefundStatus")
    @ApiOperation(value = "线下退款")
    public R updateRefundStatus(@Valid @RequestBody DeleteOrderPo po) {
        Integer id = po.getId();
        int i = orderService.updateRefundStatus(id);
        return R.ok().put("rows", i);
    }

}
