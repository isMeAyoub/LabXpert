package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.RoleDTO;
import com.simplon.labxpert.model.entity.Role;
import org.mapstruct.Mapper;
/**
 * Mapper for the Role entity.
 * It contains all the methods that we need to map a RoleDTO to a Role and vice versa.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends GlobalMapper<RoleDTO, Role> {
}
