package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.service.domain.Appointment;
import com.Yu.his.service.po.FrontAppointmentSearchPo;
import com.Yu.his.service.po.MisAppointmentQueryPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AppointmentMapper extends CommonMapper<Appointment> {
    List<HashMap> searchAppointmentByPage(FrontAppointmentSearchPo po);

    long searchAppointmentCount(FrontAppointmentSearchPo po);

    List<HashMap> searchByPageForMis(MisAppointmentQueryPo po);

    long searchByPageForMisCount(MisAppointmentQueryPo po);

    int deleteById(Integer[] ids);

}