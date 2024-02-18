package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.ReagentMappper;
import com.simplon.labxpert.model.dto.ReagentDTO;
import com.simplon.labxpert.model.entity.Reagent;
import com.simplon.labxpert.model.enums.ReagentStatus;
import com.simplon.labxpert.repository.ReagentRepository;
import com.simplon.labxpert.service.ReagentService;
import com.simplon.labxpert.util.UtilMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of the Reagent service.
 * It contains the methods that the service will implement.
 */
@Service
public class ReagentServiceImpl implements ReagentService {
    private final static String REAGENG_NOT_FOUND = "Reagent not found with id: ";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ReagentRepository reagentRepository;
    private final ReagentMappper reagentMappper;
    private final UtilMethods utilMethods;

    @Autowired
    public ReagentServiceImpl(ReagentRepository reagentRepository, ReagentMappper reagentMappper, UtilMethods utilMethods) {
        this.reagentRepository = reagentRepository;
        this.reagentMappper = reagentMappper;
        this.utilMethods = utilMethods;
    }

    @Override
    public List<ReagentDTO> getAllReagents() {
        try {
            LOGGER.info("Getting all reagents");
            List<Reagent> reagentList = reagentRepository.findAll();
            return reagentList.stream().map(reagentMappper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching all reagents", e);
            throw e;
        }
    }

    @Override
    public List<ReagentDTO> getAllReagentsByStatus(ReagentStatus status) {
        try {
            LOGGER.info("Fetching regents with this status: {}", status);
            // TODO: TO @Ayoub ait si ahmad CHECK IF THE STATUS IS NULL OR NOT
            List<Reagent> reagentList = reagentRepository.findAllByReagentStatus(status);
            return reagentList.stream().map(reagentMappper::toDTO).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.error("Error occurred while fetching all reagents by status", exception);
            throw exception;
        }
    }

    @Override
    public List<ReagentDTO> getAllReagentsThatWillExpireSoon(int days){
        try {
            LOGGER.info("Fetching all reagents that will expire soon");
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime afterDays = today.plusDays(days);
            List<Reagent> reagentList = reagentRepository.findAll().stream().filter(reagent -> reagent.getExpirationDate().isAfter(today) && reagent.getExpirationDate().isBefore(afterDays)).collect(Collectors.toList());
            return reagentList.stream().map(reagentMappper::toDTO).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.error("Error occurred while fetching all reagents that will expire soon", exception);
            throw exception;
        }
    }

    @Override
    public List<ReagentDTO> getAllReagentsThatExpired() {
        try {
            LOGGER.info("Fetching all reagents that expired");
            LocalDateTime today = LocalDateTime.now();
            List<Reagent> reagentList = reagentRepository.findAll().stream().filter(reagent -> reagent.getExpirationDate().isBefore(today)).collect(Collectors.toList());
            return reagentList.stream().map(reagentMappper::toDTO).collect(Collectors.toList());
        } catch (Exception exception) {
            LOGGER.error("Error occurred while fetching all reagents that expired", exception);
            throw exception;
        }
    }

    @Override
    public ReagentDTO getReagentById(long reagentID) {
        try {
            LOGGER.info("Fetching reagent with ID: {}", reagentID);
            Optional<Reagent> reagent = reagentRepository.findById(reagentID);
            if (!reagent.isPresent()) {
                throw new CustomNotFoundException(REAGENG_NOT_FOUND + reagentID, HttpStatus.NOT_FOUND);
            } else {
                return reagentMappper.toDTO(reagent.get());
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching reagent with ID: {}", reagentID, e);
            throw e;
        }
    }

    @Override
    public ReagentDTO addReagent(ReagentDTO reagentDTO) {
        Reagent reagent = reagentMappper.toEntity(reagentDTO);
        Optional<Reagent> reagentOptional = reagentRepository.findByReagentNameIgnoreCase(reagent.getReagentName());
        if (reagentOptional.isPresent()) {
            reagent = reagentOptional.get();
            throw new CustomNotFoundException("Reagent already exists with name: " + reagent.getReagentName(), HttpStatus.CONFLICT);
        } else {
            // do the validation of the expirationDate
            LocalDateTime today = LocalDateTime.now();
            if (reagent.getExpirationDate().isBefore(today)) {
                throw new CustomNotFoundException("The expiration date is in The past ", HttpStatus.BAD_REQUEST);
            }
            reagent.setReagentStatus(ReagentStatus.IN_STOCK_VALID);
            reagent.setReagentSerialNumber(UtilMethods.generateSerialNumber());
            return reagentMappper.toDTO(reagentRepository.save(reagent));
        }
    }

    @Override
    public ReagentDTO updateReagent(ReagentDTO reagentDTO, long reagentID) {
        LOGGER.info("Try To update a reagent info of this ID : {}", reagentID);
        Optional<Reagent> reagentOptional = reagentRepository.findById(reagentID);
        if (!reagentOptional.isPresent()) {
            throw new CustomNotFoundException(REAGENG_NOT_FOUND + reagentID, HttpStatus.NOT_FOUND);
        }
        Reagent reagent = reagentMappper.toEntity(reagentDTO);
        reagent.setReagentID(reagentID);
        reagent.setReagentSerialNumber(reagentOptional.get().getReagentSerialNumber());
        reagent.setReagentStatus(reagentOptional.get().getReagentStatus());
        validateReagent(reagent, reagentID);
        Reagent reagentSaved = reagentRepository.save(reagent);
        return reagentMappper.toDTO(reagentSaved);
    }

    private void validateReagent(Reagent reagent, long reagentID) {
        try {
            LOGGER.info("Validating user with ID: {}", reagentID);
            if (reagent.getReagentStatus() == null || reagent.getReagentStatus().equals(ReagentStatus.EXPIRED)) {
                throw new CustomNotFoundException("You can't update a reagent with status expired", HttpStatus.BAD_REQUEST);
            }
            Optional<Reagent> reagentOptional = reagentRepository.findByReagentNameAndReagentIDNot(reagent.getReagentName(), reagentID);
            if (reagentOptional.isPresent()) {
                throw new CustomNotFoundException("The reagent Name that you provide is already taken", HttpStatus.BAD_REQUEST);
            }
            if (reagent.getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new CustomNotFoundException("The expiration date is in The past ", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            LOGGER.error("Error occurred while validating Reagent with ID: {}", reagentID, exception);
            throw exception;
        }
    }

    @Override
    public String deleteReagent(long reagentID) {
        try {
            LOGGER.info("delete the reagent By id: {}", reagentID);
            Optional<Reagent> reagentOptional = reagentRepository.findById(reagentID);
            if (!reagentOptional.isPresent()) {
                LOGGER.warn("the reagent with this id not exists");
                throw new CustomNotFoundException(REAGENG_NOT_FOUND + reagentID, HttpStatus.NOT_FOUND);
            } else {
                Reagent reagent = reagentOptional.get();
                LOGGER.info("Reagent was deleted with this id: {}", reagentID);
                reagentRepository.delete(reagent);
                return "Reagent was deleted with this id: " + reagentID;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting reagent with ID: {}", reagentID, e);
            throw e;
        }
    }

    @Scheduled(fixedRate = 60000)
    public void updateReagentStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<Reagent> allReagents = reagentRepository.findAll();
        for (Reagent reagent : allReagents) {
            if (reagent.getExpirationDate().isBefore(now)) {
                reagent.setReagentStatus(ReagentStatus.EXPIRED);
                reagentRepository.save(reagent);
            }
        }
    }
}
