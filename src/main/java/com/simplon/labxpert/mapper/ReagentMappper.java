package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.ReagentDTO;
import com.simplon.labxpert.model.entity.Reagent;
import org.mapstruct.Mapper;
/**
 * Mapper for the Reagent entity.
 * It contains all the methods that we need to map a ReagentDTO to a Reagent and vice versa.
 */
@Mapper(componentModel = "spring")
public interface ReagentMappper extends GlobalMapper<ReagentDTO,Reagent>{
}
