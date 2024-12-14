package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.service.domain.User;
import com.Yu.his.service.po.UserQueryPo;
import com.Yu.his.service.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface UserMapper extends CommonMapper<User> {

    Set<String> selectUserPermissions(int id);

    List<UserListVO> selectUserList(UserQueryPo po);

    long Count(UserQueryPo po);
}