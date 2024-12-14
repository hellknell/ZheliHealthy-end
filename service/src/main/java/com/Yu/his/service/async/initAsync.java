package com.Yu.his.service.async;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.domain.AppointmentRestriction;
import com.Yu.his.service.domain.AppointmentRestrictionField;
import com.Yu.his.service.domain.System;
import com.Yu.his.service.domain.SystemField;
import com.Yu.his.service.mapper.AppointmentRestrictionMapper;
import com.Yu.his.service.mapper.SystemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/13 22:03
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class initAsync {
    final AppointmentRestrictionMapper appointmentRestrictionMapper;
    final SystemMapper systemMapper;
    final RedisTemplate redisTemplate;

    @Async(value = "AsyncTaskExecutor")
    public void init() {
        setSystemCache();
        createAppointRestrictionCache();
    }

    private void setSystemCache() {
        MyBatisWrapper<System> wrapper = new MyBatisWrapper<>();
        wrapper.select(SystemField.Id, SystemField.Value, SystemField.Item);
        List<System> list = systemMapper.list(wrapper);
        list.forEach(item -> {
            String item1 = item.getItem();
            String value = item.getValue();
            String key = "system#" + item1;
            redisTemplate.opsForValue().set(key, value);
        });
        log.debug("系统缓存设置成功");
    }

    private void createAppointRestrictionCache() {
        DateTime startDate = DateUtil.tomorrow();
        DateTime endDate = startDate.offsetNew(DateField.DAY_OF_MONTH, 60);
        DateRange range = DateUtil.range(startDate, endDate, DateField.DAY_OF_MONTH);
        MyBatisWrapper<AppointmentRestriction> wrapper = new MyBatisWrapper<>();
        wrapper.select(AppointmentRestrictionField.Date, AppointmentRestrictionField.Num1, AppointmentRestrictionField.Num3).whereBuilder().andBetween(AppointmentRestrictionField.Date, startDate.toJdkDate(), endDate.toJdkDate());
        List<AppointmentRestriction> list = appointmentRestrictionMapper.list(wrapper);
        range.forEach(one -> {
            String dateStr = one.toDateStr();
            String key = "system#appointment_number";
            int maxNum = Integer.parseInt((String) redisTemplate.opsForValue().get(key));
            int realNum = 0;
            for (AppointmentRestriction one1 : list) {
                DateTime dateTime = new DateTime(one1.getDate());
                String dateStr1 = dateTime.toDateStr();
                if (dateStr1.equals(dateStr)) {
                    maxNum = one1.getNum1();
                    realNum = one1.getNum3();
                    break;
                }
            }
            HashMap map = new HashMap<>();
            map.put("maxNum", maxNum);
            map.put("realNum", realNum);
            String key1 = "appointment#" + dateStr;
            redisTemplate.opsForHash().putAll(key1, map);
            DateTime dateTime = new DateTime(dateStr).offsetNew(DateField.DAY_OF_MONTH, 1);
            redisTemplate.expireAt(key1, dateTime);
        });
        log.debug("未来60体检套餐设置完成");

    }

}
