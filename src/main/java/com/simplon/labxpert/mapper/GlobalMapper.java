package com.simplon.labxpert.mapper;

/**
 * GlobalMapper interface.
 * It contains all the methods that we need to map a DTO to an ENTITY and vice versa.
 * @param <DTO> the DTO class
 * @param <ENTITY> the ENTITY class
 */
public interface GlobalMapper<DTO,ENTITY> {
        DTO toDTO(ENTITY e);
        ENTITY toEntity(DTO d);
}
