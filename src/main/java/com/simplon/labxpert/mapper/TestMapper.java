package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.TestDTO;
import com.simplon.labxpert.model.entity.Test;
import org.mapstruct.Mapper;
/**
 * Mapper for the Test entity.
 * It contains all the methods that we need to map a TestDTO to a Test and vice versa.
 */
@Mapper(componentModel = "spring")
public interface TestMapper extends GlobalMapper<TestDTO, Test> {
}