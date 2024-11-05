package com.Yu.his.service.service;

import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.converter.UserConverter;
import com.Yu.his.service.domain.Dept;
import com.Yu.his.service.domain.DeptField;
import com.Yu.his.service.mapper.DeptMapper;
import com.Yu.his.service.vo.DeptListVo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.Yu.his.service.domain.RoleField.Id;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/18 18:54
 */
@Service
@Api(tags = "部门实体")
@RequiredArgsConstructor
public class DeptService {
    final DeptMapper deptMapper;

    public List<DeptListVo> selectAllDept() {
        MyBatisWrapper<Dept> wrapper = new MyBatisWrapper<>();
        wrapper.select(DeptField.DeptName, DeptField.Id).orderByAsc(Id);

        List<Dept> list = deptMapper.list(wrapper);
//      \  List<DeptListVo> deptVoList = UserConverter.INSTANCE.toDeptVoList(list);
        List<DeptListVo> list1 = list.stream().map(UserConverter.INSTANCE::toDeptVo).collect(Collectors.toList());
        return list1;
    }


}
