package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.PatientDTO;
import com.simplon.labxpert.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the Patient entity.
 * It contains all the methods that we need to map a PatientDTO to a Patient and vice versa.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "samplesDTO", source = "samples")
    PatientDTO toDTO(Patient patient);

    @Mapping(target = "samples", source = "samplesDTO")
    Patient toEntity(PatientDTO patientDTO);
}
