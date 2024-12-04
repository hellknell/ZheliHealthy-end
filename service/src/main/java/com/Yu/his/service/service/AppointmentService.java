package com.Yu.his.service.service;

import com.Yu.his.generator.help.MyBatisWrapper;
import com.Yu.his.generator.help.PageInfo;
import com.Yu.his.service.converter.AppointmentConverter;
import com.Yu.his.service.domain.Appointment;
import com.Yu.his.service.mapper.AppointmentMapper;
import com.Yu.his.service.po.MisAppointmentQueryPo;
import com.Yu.his.service.vo.AppointmentRecordVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.Yu.his.service.domain.AppointmentField.*;

/**
 *功能:
 *作者:何宇
 *日期：2024/11/15 16:38
 */
@Service("MisAppointmentService")
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    final AppointmentMapper appointmentMapper;

    public List<AppointmentRecordVo> searchByOrderId(Integer orderId) {
        MyBatisWrapper<Appointment> wrapper = new MyBatisWrapper<>();
        wrapper.select(Id, Name, Sex, Tel, Date, Status, Birthday, Date).whereBuilder().andEq(setOrderId(orderId));
        List<Appointment> list = appointmentMapper.list(wrapper.orderByDesc(Id));
        return list.stream().map(AppointmentConverter.INSTANCE::toAppointmentRecordVo).collect(Collectors.toList());
    }


    public PageInfo searchByPage(MisAppointmentQueryPo po) {
        List<HashMap> list = new ArrayList<>();
        long l = appointmentMapper.searchByPageForMisCount(po);
        if (l > 0L) {
            list = appointmentMapper.searchByPageForMis(po);
        }
        return new PageInfo(po.getPageIndex(), po.getPageSize(), (int) l, list);
    }


    public int delete(Integer[] ids) {
        int i = appointmentMapper.deleteById(ids);
        return i;
    }

}
