package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.TestResultDTO;
import com.simplon.labxpert.model.entity.TestResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the TestResult entity.
 * It contains all the methods that we need to map a TestResultDTO to a TestResult and vice versa.
 */
@Mapper(componentModel = "spring")
public interface TestResultMapper {

        @Mapping(target = "testDTO", source = "test")
        @Mapping(target = "analysisDTO", source = "analysis")
        TestResultDTO toDTO(TestResult testResult);

        @Mapping(target = "test", source = "testDTO")
        @Mapping(target = "analysis", source = "analysisDTO")
        TestResult toEntity(TestResultDTO testResultDTO);
}
