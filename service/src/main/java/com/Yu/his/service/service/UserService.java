package com.Yu.his.service.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.service.domain.User;
import com.Yu.his.service.domain.UserField;
import com.Yu.his.service.exception.HisException;
import com.Yu.his.service.mapper.UserMapper;
import com.Yu.his.service.po.UpdatePassPo;
import com.Yu.his.service.po.UserLoginPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/6 12:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    final UserMapper userMapper;

    public Integer login(UserLoginPo po) {
        String userName = po.getUserName();
        String originPass = po.getPassWord();
        MD5 md5 = MD5.create();
        String temp = md5.digestHex(userName);
        String tempBefore = StrUtil.subWithLength(temp, 0, 6);
        String tempEnd = StrUtil.subSuf(temp, temp.length() - 3);
        String newPass = md5.digestHex(tempBefore + originPass + tempEnd).toUpperCase();
        MyBatisWrapper<User> wrapper = new MyBatisWrapper<>();
        wrapper.select(UserField.Id).whereBuilder().andEq(UserField.setUsername(userName)).andEq(UserField.setPassword(newPass));
        User user = userMapper.topOne(wrapper);
        if (ObjectUtil.isNull(user)) {
            throw new HisException("用户名或密码错误");
        }
        return user.getId();
    }


    public Integer updatePass(UpdatePassPo po) {
        String password = po.getPassword();
        String newPassword = po.getNewPassword();
        int userId = po.getUserId();

        MyBatisWrapper<User> wrapper = new MyBatisWrapper<>();
        wrapper.select(UserField.Username).whereBuilder().andEq(UserField.setId(userId));
        User user = userMapper.topOne(wrapper);
        String dbUserName = user.getUsername();
        MD5 md5 = MD5.create();
        String temp = md5.digestHex(dbUserName);
        String tempBefore = StrUtil.subWithLength(temp, 0, 6);
        String tempEnd = StrUtil.subSuf(temp, temp.length() - 3);
        String newPass = md5.digestHex(tempBefore + password + tempEnd).toUpperCase();

        newPassword = md5.digestHex(tempBefore + newPassword + tempEnd).toUpperCase();
        MyBatisWrapper<User> wrapper1 = new MyBatisWrapper<>();
        wrapper1.update(UserField.setPassword(newPassword)).whereBuilder().andEq(UserField.setId(userId)).andEq(UserField.setPassword(newPass));
        int i = userMapper.updateField(wrapper1);
        if(i==0){
         throw new HisException("原密码错误") ;
        }
        return i;
    }
}
