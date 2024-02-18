package com.simplon.labxpert.controller;

import com.simplon.labxpert.model.dto.RoleDTO;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller class for managing roles.
 *
 * @Author Ayoub Ait Si Ahmad
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Creates a new role.
     *
     * @param roleDTO the details of the role to create.
     * @return the created role.
     */
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        LOGGER.info("Creating role: {}", roleDTO);
        RoleDTO createdRole = userService.saveRole(roleDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    /**
     * Adds a role to a user.
     *
     * @param roleToUser the details of the role to add to the user.
     * @return a message indicating the role was added.
     */
    @PostMapping("/addRoleToUser")
    public ResponseEntity<String> addRoleToUser(@RequestBody RoleToUser roleToUser) {
        LOGGER.info("Adding role {} to user {}", roleToUser.getRoleName(), roleToUser.getUsername());
        userService.addRoleToUser(roleToUser.getUsername(), roleToUser.getRoleName());
        return new ResponseEntity<>("Role added to user", HttpStatus.OK);
    }
}

@Data
class RoleToUser {
    private String username;
    private String roleName;
}

