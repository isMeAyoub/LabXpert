package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.AnalysisDTO;
import com.simplon.labxpert.model.entity.Analysis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the Analysis entity.
 * It contains all the methods that we need to map a AnalysisDTO to a Analysis and vice versa.
 */
@Mapper(componentModel = "spring", uses = {SampleMapper.class, AnalysisReagentMapper.class, TestResultMapper.class})
public interface AnalysisMapper {


    @Mapping(target = "sampleDTO", source = "sample")
    @Mapping(target = "analysisReagentDTOs", source = "analysisReagents")
    @Mapping(target = "testResultDTOs", source = "testResults")
    AnalysisDTO toDTO(Analysis analysis);


    @Mapping(target = "sample", source = "sampleDTO")
    @Mapping(target = "analysisReagents", source = "analysisReagentDTOs")
    @Mapping(target = "testResults", source = "testResultDTOs")
    Analysis toEntity(AnalysisDTO analysisDTO);
}
