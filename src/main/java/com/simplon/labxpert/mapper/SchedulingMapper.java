package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.SchedulingDTO;
import com.simplon.labxpert.model.entity.Scheduling;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the Scheduling entity.
 * It contains all the methods that we need to map a SchedulingDTO to a Scheduling and vice versa.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AnalysisMapper.class})
public interface SchedulingMapper {
    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "analysis", target = "analysisDTO")
    SchedulingDTO toDTO(Scheduling scheduling);

    @Mapping(source = "userDTO", target = "user")
    @Mapping(source = "analysisDTO", target = "analysis")
    Scheduling toEntity(SchedulingDTO schedulingDTO);
}
