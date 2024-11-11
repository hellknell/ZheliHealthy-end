package com.Yu.his.service.converter;


import com.Yu.his.service.domain.Appointment;
import com.Yu.his.service.vo.AppointmentRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AppointmentConverter {

    AppointmentConverter INSTANCE = Mappers.getMapper(AppointmentConverter.class);

    @Mapping(target = "age", expression = "java(cn.hutool.core.date.DateUtil.ageOfNow(appointment.getBirthday()))")
    AppointmentRecordVo toAppointmentRecordVo(Appointment appointment);
}
