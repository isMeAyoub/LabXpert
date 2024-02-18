package com.simplon.labxpert.mapper;

import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.model.entity.User;
import org.mapstruct.Mapper;
/**
 * Mapper for the User entity.
 * It contains all the methods that we need to map a UserDTO to a User and vice versa.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends GlobalMapper<UserDTO, User>{
}
