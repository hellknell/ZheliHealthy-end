package com.Yu.his.service.websocket;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.Yu.his.service.config.StpCustomerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/6 21:16
 */

@ServerEndpoint("/socket")
@Slf4j
@Component
public class WebsocketService {
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnClose
    public void onClose(Session session) {
        Map<String, Object> userProperties = session.getUserProperties();
        if (userProperties.containsKey("userId")) {
            String userId = userProperties.get("userId").toString();
            sessionMap.remove(userId);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("请求。。");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        JSONObject json = JSONUtil.parseObj(message);
        log.info(json.toString());
        //操作类型
        String opt = json.getStr("opt");
        //角色身份
        String identity = json.getStr("identity");
        //token
        String token = json.getStr("token");

        String userId = null;
        if (identity.equals("customer")) {
            userId = "customer_" + StpCustomerUtil.stpLogic.getLoginIdByToken(token).toString();
        } else {
            //业务端
            userId = "user_" + StpUtil.getTokenValueByLoginId(token).toString();
        }
        log.info(userId);
        Map map = session.getUserProperties();
        map.put("userId", userId);
        if (sessionMap.containsKey(userId)) {
            sessionMap.replace(userId, session);
        } else {
            sessionMap.put(userId, session);
        }
        if ("ping".equals(opt)) {
            return;
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        log.error("发生错误", e);
    }

    public static void sendInfo(String message, String userId) {
        userId = "customer_" + userId;
        if (StrUtil.isNotBlank(userId) && sessionMap.containsKey(userId)) {
            log.info("向用户{}发送了信息", userId);
            Session session = sessionMap.get(userId);
            sendMessage(message, session);
}

    }

    private static void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发生异常", e);
        }
    }


}
