package com.Yu.his.service.config;

import cn.dev33.satoken.stp.StpLogic;
import org.springframework.stereotype.Component;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/4 22:43
 */
@Component
public class StpCustomerUtil {

    public StpCustomerUtil() {
    }

    public final static String TYPE = "customer";
    public static StpLogic stpLogic = new StpLogic(TYPE);


    public String getLoginType() {
        return stpLogic.getLoginType();
    }
}
