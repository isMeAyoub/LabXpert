package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.AnalysisReagentDTO;
import com.simplon.labxpert.model.entity.AnalysisReagent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/**
 * Mapper for the AnalysisReagent entity.
 * It contains all the methods that we need to map a AnalysisReagentDTO to a AnalysisReagent and vice versa.
 */
@Mapper(componentModel = "spring")
public interface AnalysisReagentMapper {


    @Mapping(target = "analysisDTO", source = "analysis")
    @Mapping(target = "reagentDTO", source = "reagent")
    AnalysisReagentDTO toDTO(AnalysisReagent analysisReagent);


    @Mapping(target = "analysis", source = "analysisDTO")
    @Mapping(target = "reagent", source = "reagentDTO")
    AnalysisReagent toEntity(AnalysisReagentDTO analysisReagentDTO);
}
