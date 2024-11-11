package com.Yu.his.service.schedule;

import com.Yu.his.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/5 19:44
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCleanSchedule {
    final OrderMapper orderMapper;

    /**
     * 每小时执行一次
     */
    @Transactional
    @Scheduled(cron = "0 0 * * * ?")
    public void closeTimeoutOrder() {
        int i = orderMapper.closeTimeoutOrder();
        if (i > 0) {
            log.info("共有" + i + "个订单被关闭");
        }
    }
}
