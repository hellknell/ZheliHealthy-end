package com.Yu.his.service.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.config.AliPayConfig;
import com.Yu.his.service.config.StpCustomerUtil;
import com.Yu.his.service.po.CreatePaymentPo;
import com.Yu.his.service.po.FrontOrderSearchPo;
import com.Yu.his.service.po.OrderCancelPo;
import com.Yu.his.service.po.OrderRefundPo;
import com.Yu.his.service.service.user.OrderService;
import com.Yu.his.service.vo.R;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 17:44
 */
@RestController("FrontOrderController")
@Slf4j
@Api(tags = "订单模块")
@RequiredArgsConstructor
@RequestMapping("/front/order")
public class OrderController {
    final OrderService orderService;

    @GetMapping("/createPayment")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    @ApiOperation("创建订单")
    public void createPayment(@RequestParam("goodsId") @NotNull(message = "goodsId不能为空") @Min(value = 1, message = "goods不能小于1") Integer goodsId, @RequestParam("number") Integer number, @RequestParam(value = "amount", required = false) String amount, @RequestParam(value = "outTradeNo", required = false) String outTradeNo, @RequestParam(value = "goodsTitle", required = false) String goodsTitle, HttpServletResponse httpResponse) throws Exception {
        CreatePaymentPo po = new CreatePaymentPo();
        po.setNumber(number);
        po.setGoodsId(goodsId);
        int customerId = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        po.setCustomerId(customerId);
        HashMap payment;
        if (outTradeNo == null) {
            payment = orderService.createPayment(po);
        } else {
            log.info("订单页面");
            payment = new HashMap() {{
                put("out_trade_no", outTradeNo);
                put("total_amount", amount);
                put("subject", goodsTitle);
                put("body", goodsTitle);
            }};
        }
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.appId, AliPayConfig.merchantPrivateKey, "json", AliPayConfig.charset, AliPayConfig.alipayPublicKey, AliPayConfig.signType);
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(AliPayConfig.notifyUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", MapUtil.getStr(payment, "out_trade_no"));  // 我们自己生成的订单编号
        bizContent.set("total_amount", MapUtil.getStr(payment, "total_amount")); // 订单的总金额
        bizContent.set("subject", MapUtil.getStr(payment, "subject"));   // 支付的名称
        bizContent.set("body", MapUtil.getStr(payment, "body"));   // 支付的名称
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + "UTF-8");
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @GetMapping("/searchOrder")
    @ApiOperation("查询订单")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    public R searchFrontOrderByPage(@Valid FrontOrderSearchPo po) {
        int customerId = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        Integer pageIndex = po.getPageIndex();
        Integer i = (pageIndex - 1) * po.getPageSize();
        po.setStart(i);
        po.setCustomerId(customerId);
        PageInfo pageInfo = orderService.searchFrontOrderByPage(po);
        return R.ok().put("page", pageInfo);
    }

    @PostMapping("/refund")
    @ApiOperation("订单退款")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    public R refund(@RequestBody @Valid OrderRefundPo po) {
        int loginIdAsInt = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        po.setCustomerId(loginIdAsInt);
        boolean refund = orderService.refund(po);
        return R.ok().put("result", refund);
    }

    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    @SaCheckLogin(type = StpCustomerUtil.TYPE)
    public R cancel(@RequestBody @Valid OrderCancelPo po) {
        int loginIdAsInt = StpCustomerUtil.stpLogic.getLoginIdAsInt();
        po.setCustomerId(loginIdAsInt);
        int cancel = orderService.cancel(po);
        return R.ok().put("rows", cancel);
    }
}

