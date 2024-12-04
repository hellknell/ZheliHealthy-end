package com.Yu.his.service.schedule;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/14 8:20
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentSchedule {
    final RedisTemplate redisTemplate;
    @Scheduled(cron = "0 0 23 * * ?")
    private void createAppointment62() {
        String key = "system#appointment_number";
        int maxNum = Integer.parseInt(redisTemplate.opsForValue().get(key).toString());
        int realNum = 0;
//        未来61天的缓存
        DateTime dateTime = new DateTime().offsetNew(DateField.DAY_OF_MONTH, 62);
        String dateStr = dateTime.toDateStr();
        String key1 = "appointment#" + dateStr;
        HashMap map = new HashMap() {{
            put("maxNum", maxNum);
            put("realNum", realNum);
        }};
        redisTemplate.opsForHash().putAll(key1, map);
        DateTime dateTime1 = DateTime.now().offsetNew(DateField.DAY_OF_MONTH, 1);
        redisTemplate.expireAt(key1, dateTime1);
        log.debug("生成了" + dateStr + "的缓存");
    }

}
