package com.simplon.labxpert.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.model.dtoViews.Views;
import com.simplon.labxpert.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing users.
 * @Author Ayoub Ait Si Ahmad
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Fetches all users.
     * @return a list of all users.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        LOGGER.info("Fetching all users");
        List<UserDTO> userDTOS = userService.getAllUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    /**
     * Fetches a user by their ID.
     * @param id the ID of the user to fetch.
     * @return the user with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        LOGGER.info("Fetching user with ID: {}", id);
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * Creates a new user.
     * @param userDTO the details of the user to create.
     * @return the created user.
     */
    @PostMapping
    @JsonView(Views.CreateUser.class)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        LOGGER.info("Creating new user");
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * @param userDTO the new details of the user.
     * @param id the ID of the user to update.
     * @return the updated user.
     */
    @PutMapping("/{id}")
    @JsonView(Views.UpdateUser.class)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable long id) {
        LOGGER.info("Updating user with ID: {}", id);
        UserDTO updatedUser = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    /**
     * Deletes a user.
     * @param id the ID of the user to delete.
     * @return a response indicating the user was deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        LOGGER.info("Deleting user with ID: {}", id);
        return userService.deleteUser(id);
    }
}