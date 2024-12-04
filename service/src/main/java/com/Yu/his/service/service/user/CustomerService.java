package com.Yu.his.service.service.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.domain.Customer;
import com.Yu.his.service.domain.CustomerField;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.mapper.CustomerMapper;
import com.Yu.his.service.mapper.OrderMapper;
import com.Yu.his.service.po.CustomerUpdatePo;
import com.Yu.his.service.vo.OrderStatisticVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/2 17:50
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    final RedisTemplate redisTemplate;
    final CustomerMapper customerMapper;
    final OrderMapper orderMapper;

    public Boolean sendSmsCode(String tel) {

        String code = RandomUtil.randomNumbers(6);
        log.info(code);

        String key = "sms_code_refresh_" + tel;
        if (redisTemplate.hasKey(key)) {
            return false;
        }
        redisTemplate.opsForValue().set(key, code);
        redisTemplate.expire(key, 2, TimeUnit.MINUTES);

        key = "sms_code_" + tel;
        redisTemplate.opsForValue().set(key, code);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);

        return true;
    }


    public int login(String tel, String code) {
        String key = "sms_code_" + tel;
        if (!redisTemplate.hasKey(key)) {
            throw new HisException("验证码已过期");
        }
        String value = redisTemplate.opsForValue().get(key).toString();
        if (!StrUtil.equals(value, code)) {
            throw new HisException("验证码不正确");
        }
        redisTemplate.delete(key);
        key = "sms_code_refresh_" + tel;
        redisTemplate.delete(key);
        MyBatisWrapper<Customer> wrapper = new MyBatisWrapper<>();
        wrapper.select(CustomerField.Id).whereBuilder().andEq(CustomerField.setTel(tel));
        Customer customer = customerMapper.topOne(wrapper);
        if (customer == null) {
            Customer customer1 = new Customer();
            customer1.setTel(tel);
            customerMapper.insertSelective(customer1);
            Integer id = customer1.getId();
            log.info("id为{}", id);
            return id;
        } else {
            return customer.getId();
        }
    }

    public OrderStatisticVo searchSummary(int id) {
        MyBatisWrapper<Customer> wrapper = new MyBatisWrapper<>();
        wrapper.select(CustomerField.Name, CustomerField.Sex, CustomerField.Tel, CustomerField.Photo, CustomerField.CreateTime).whereBuilder().andEq(CustomerField.setId(id));
        Customer customer = customerMapper.topOne(wrapper);
        OrderStatisticVo orderStatisticVo = orderMapper.searchOrderStatistic(id);
        orderStatisticVo.setName(customer.getName());
        orderStatisticVo.setSex(customer.getSex());
        orderStatisticVo.setPhoto(customer.getPhoto());
        orderStatisticVo.setCreateTime(customer.getCreateTime());
        orderStatisticVo.setTel(customer.getTel());
        return orderStatisticVo;
    }

    public int update(CustomerUpdatePo po) {
        Customer customer = new Customer();
        customer.setSex(po.getSex());
        customer.setName(po.getName());
        customer.setTel(po.getTel());
        customer.setId(po.getId());
        customer.setPhoto(po.getPhoto());
        int i = customerMapper.updateByPrimaryKeySelective(customer);
        return i;
    }
}
