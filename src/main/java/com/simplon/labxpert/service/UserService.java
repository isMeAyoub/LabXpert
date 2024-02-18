package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.RoleDTO;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.model.entity.Role;
import com.simplon.labxpert.model.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
/**
 * Interface for the User service.
 * It contains the methods that the service will implement.
 */
public interface UserService {
    UserDTO getUserById(long id);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO,long id);
    ResponseEntity<String> deleteUser(long id);

    // For the authentication and authorization process
    User getUser(String username);
    RoleDTO saveRole(RoleDTO role);
    void addRoleToUser(String username, String roleName);

}
