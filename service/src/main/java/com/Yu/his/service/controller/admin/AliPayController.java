package com.Yu.his.service.controller.admin;

import cn.hutool.json.JSONObject;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.config.AliPayConfig;
import com.Yu.his.service.domain.Order;
import com.Yu.his.service.domain.OrderField;
import com.Yu.his.service.mapper.OrderMapper;
import com.Yu.his.service.service.user.OrderService;
import com.Yu.his.service.websocket.WebsocketService;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/alipay")
@RequiredArgsConstructor
public class AliPayController {

    //签名方式
    final OrderService orderService;
    final OrderMapper orderMapper;

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }
            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, AliPayConfig.alipayPublicKey, "UTF-8"); // 验证签名
            log.info(checkSignature + "");
            // 支付宝验签
            if (!checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                boolean b = orderService.updatePayment(new HashMap() {{
                    put("transactionId", alipayTradeNo);
                    put("outTradeNo", tradeNo);
                }});
                if (b) {
                    log.info("订单付款成功,订单状态更新成功");
                    MyBatisWrapper<Order> wrapper = new MyBatisWrapper<>();
                    wrapper.select(OrderField.CustomerId).whereBuilder().andEq(OrderField.setOutTradeNo(tradeNo));
                    Order order = orderMapper.topOne(wrapper);
                    if (order == null) {
                        log.error("没有查询到用户记录");
                    } else {
                        Integer customerId = order.getCustomerId();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("result", true);
                        log.info(order.getCustomerId() + "");
                        WebsocketService.sendInfo(jsonObject.toString(), customerId.toString());
                    }
                } else {
                    log.error("订单付款成功,但订单状态更新失败");
                }
            }

        }
        return "success";
    }
//    @GetMapping("/return")
//    public String returnPay( AliPay aliPay) throws AlipayApiException {
//
//        return "已支付完成!";
//    }
    // 7天无理由退款
}