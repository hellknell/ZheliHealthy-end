package com.Yu.his.service.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import com.Yu.his.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限验证接口扩展
 */
@Component
@RequiredArgsConstructor
@Slf4j
// 保证此类被springboot扫描，完成sa-token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getPermissionList(Object userId, String loginKey) {
        List<String> list = new ArrayList<>();
        Integer id = Integer.valueOf(userId.toString());
        Set<String> permissions = userMapper.selectUserPermissions(id);
        list.addAll(permissions);
        return list;

    }

    /**
     * 返回一个账号所拥有的权限码集合 
     */

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        return list;
    }

}
