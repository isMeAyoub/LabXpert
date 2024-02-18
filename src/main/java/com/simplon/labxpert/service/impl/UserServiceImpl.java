package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.RoleMapper;
import com.simplon.labxpert.mapper.UserMapper;
import com.simplon.labxpert.model.dto.RoleDTO;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.model.entity.Role;
import com.simplon.labxpert.model.entity.User;
import com.simplon.labxpert.repository.RoleRepository;
import com.simplon.labxpert.repository.UserRepository;
import com.simplon.labxpert.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the User service.
 * It contains the methods that the service will implement.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USER_NOT_FOUND = "User not found with id: ";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching user with username: {}", username, e);
            throw new UsernameNotFoundException("Error occurred while fetching user with username: " + username, e);
        }

        if (user.isPresent()) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.get().getRoles().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()))
            );
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Override
    public UserDTO getUserById(long id) {
        try {
            LOGGER.info("Fetching user with ID: {}", id);
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new CustomNotFoundException(USER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            } else {
                return userMapper.toDTO(user.get());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        try {
            LOGGER.info("Fetching user with username: {}", username);
            Optional<User> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new CustomNotFoundException("User not found with username: " + username, HttpStatus.NOT_FOUND);
            } else {
                return userMapper.toDTO(user.get());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching user with username: {}", username, e);
            throw e;
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            LOGGER.info("Fetching user with email: {}", email);
            Optional<User> user = userRepository.findByEmail(email);
            if (!user.isPresent()) {
                throw new CustomNotFoundException("User not found with email: " + email, HttpStatus.NOT_FOUND);
            } else {
                return userMapper.toDTO(user.get());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching user with email: {}", email, e);
            throw e;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            LOGGER.info("Fetching all users");
            List<User> users = userRepository.findAll();
            return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching all users", e);
            throw e;
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            LOGGER.info("Creating new user");
            User user = userMapper.toEntity(userDTO);
            validateUser(user);
            user.setIsEmailVerified(false);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return userMapper.toDTO(savedUser);
        } catch (Exception e) {
            LOGGER.error("Error occurred while creating user", e);
            throw e;
        }
    }

    private void validateUser(User user) {
        try {
            LOGGER.info("Validating user");
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                throw new CustomNotFoundException("Username already exists", HttpStatus.BAD_REQUEST);
            }
            userOptional = userRepository.findByEmail(user.getEmail());
            if (userOptional.isPresent()) {
                throw new CustomNotFoundException("Email already exists", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while validating user", e);
            throw e;
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, long id) {
        try {
            User user = userMapper.toEntity(userDTO);
            LOGGER.info("Updating user with ID: {}", id);
            Optional<User> existingUserOptional = userRepository.findById(id);
            if (!existingUserOptional.isPresent()) {
                throw new CustomNotFoundException(USER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
            validateUser(user, id);
            User existingUser = existingUserOptional.get();
            existingUser.setUserID(id);
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setPersonalInfo(user.getPersonalInfo());
            userRepository.save(existingUser);
            return userMapper.toDTO(existingUser);
        } catch (Exception e) {
            LOGGER.error("Error occurred while updating user with ID: {}", id, e);
            throw e;
        }
    }

    private void validateUser(User user, Long id) {
        try {
            LOGGER.info("Validating user with ID: {}", id);
            Optional<User> userOptional = userRepository.findByUsernameAndUserIDNot(user.getUsername(), id);
            if (userOptional.isPresent()) {
                throw new CustomNotFoundException("Username already exists", HttpStatus.BAD_REQUEST);
            }
            userOptional = userRepository.findByEmailAndUserIDNot(user.getEmail(), id);
            if (userOptional.isPresent()) {
                throw new CustomNotFoundException("Email already exists", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while validating user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(long id) {
        try {
            LOGGER.info("Deleting user with ID: {}", id);
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                throw new CustomNotFoundException(USER_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
            userRepository.delete(userOptional.get());
            return new ResponseEntity<>("User With id : " + id + " deleted succefully ", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting user with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public User getUser(String username) {
        try {
            LOGGER.info("Fetching user with username: {}", username);
            Optional<User> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new CustomNotFoundException("User not found with username: " + username, HttpStatus.NOT_FOUND);
            } else {
                return user.get();
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching user with username: {}", username, e);
            throw e;
        }
    }

    @Override
    public RoleDTO saveRole(RoleDTO role) {
        try {
            LOGGER.info("Saving role");
            Role savedRole = roleRepository.save(roleMapper.toEntity(role));
            return roleMapper.toDTO(savedRole);
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving role", e);
            throw e;
        }
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        try {
            LOGGER.info("Adding role to user");
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (!userOptional.isPresent()) {
                throw new CustomNotFoundException("User not found with username: " + username, HttpStatus.NOT_FOUND);
            }
            Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
            if (!roleOptional.isPresent()) {
                throw new CustomNotFoundException("Role not found with name: " + roleName, HttpStatus.NOT_FOUND);
            }
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.getRoles().add(role);
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error occurred while adding role to user", e);
            throw e;
        }
    }

}