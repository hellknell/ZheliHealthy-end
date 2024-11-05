package com.Yu.his.service.service;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 19:19
 */

import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.converter.UserConverter;
import com.Yu.his.service.domain.Role;
import com.Yu.his.service.mapper.RoleMapper;
import com.Yu.his.service.vo.RoleListVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.Yu.his.service.domain.RoleField.Id;
import static com.Yu.his.service.domain.RoleField.RoleName;

@Service
@RequiredArgsConstructor
@Api(tags = "角色实体")
public class RoleService {
    final RoleMapper roleMapper;

    public List<RoleListVo> selectAllRole() {
        MyBatisWrapper<Role> wrapper = new MyBatisWrapper<>();
        wrapper.select(RoleName, Id).orderByAsc(Id);
        List<Role> list = roleMapper.list(wrapper);
        List<RoleListVo> list1 = list.stream().map(UserConverter.INSTANCE::toRoleVo).collect(Collectors.toList());
        return list1;
    }
}
