package com.Yu.his.service.mapper;

import com.Yu.his.generator.help.CommonMapper;
import com.Yu.his.service.domain.AppointmentRestriction;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AppointmentRestrictionMapper extends CommonMapper<AppointmentRestriction> {
    int insertOrUpdateAppointmenRestriction(Map map);
}