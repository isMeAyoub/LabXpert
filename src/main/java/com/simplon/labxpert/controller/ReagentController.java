package com.simplon.labxpert.controller;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.model.dto.ReagentDTO;
import com.simplon.labxpert.model.enums.ReagentStatus;
import com.simplon.labxpert.service.ReagentService;
import com.simplon.labxpert.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * This class is the controller layer of the Reagent entity.
 * It allows to create, read, update and delete reagents.
 * It also allows to get all reagents by status, get all expired reagents,
 * get all reagents that will expire soon and get a reagent by id.
 * @Author Ayoub Ait Si Ahmad
 */
@RestController
@RequestMapping("/api/v1/reagents")
public class ReagentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ReagentService reagentService;

    @Autowired
    public ReagentController(ReagentService reagentService) {
        this.reagentService = reagentService;
    }

    /**
     * This method allows to get all reagents.
     * @return a list of all reagents.
     */
    @GetMapping
    public ResponseEntity<List<ReagentDTO>> getAllReagents() {
        LOGGER.info("Try To Fetching All Reagents Controller layer");
        List<ReagentDTO> reagentDTOList = reagentService.getAllReagents();
        return new ResponseEntity<>(reagentDTOList, HttpStatus.OK);
    }

    /**
     * This method allows to get all reagents by status.
     * @param reagentStatus the status of the reagents to get.
     * @return a list of all reagents by status.
     */
    @GetMapping("/status")
    public ResponseEntity<List<ReagentDTO>> getAllReagentsByStatus(@RequestParam(required = true) ReagentStatus reagentStatus) {
        LOGGER.info("Try To Fetching All Reagents By Status Controller layer");
        List<ReagentDTO> reagentDTOList = reagentService.getAllReagentsByStatus(reagentStatus);
        return new ResponseEntity<>(reagentDTOList, HttpStatus.OK);
    }

    /**
     * This method allows to get a reagent by id.
     * @param id the id of the reagent to get.
     * @return the reagent with the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReagentDTO> getReagentById(@PathVariable long id) {
        LOGGER.info("Try To Fetching Reagent By Id Controller layer");
        ReagentDTO reagentDTO = reagentService.getReagentById(id);
        return new ResponseEntity<>(reagentDTO, HttpStatus.OK);
    }

    /**
     * This method allows to get all expired reagents.
     * @return a list of all expired reagents.
     */
    @GetMapping("/expired")
    public ResponseEntity<List<ReagentDTO>> getAllExpiredReagents() {
        LOGGER.info("Try To Fetching All Expired Reagents Controller layer");
        List<ReagentDTO> reagentDTOList = reagentService.getAllReagentsThatExpired();
        return new ResponseEntity<>(reagentDTOList, HttpStatus.OK);
    }

    /**
     * This method allows to get all reagents that will expire soon.
     * @param days the number of days to check.
     * @return a list of all reagents that will expire soon.
     */
    @GetMapping("/expiring-soon")
    public ResponseEntity<List<ReagentDTO>> getAllReagentsThatWillExpireSoon(@RequestParam(required = true) int days) {
        LOGGER.info("Try To Fetching All Reagents That Will Expire Soon Controller layer");
        List<ReagentDTO> reagentDTOList = reagentService.getAllReagentsThatWillExpireSoon(days);
        return new ResponseEntity<>(reagentDTOList, HttpStatus.OK);
    }

    /**
     * This method allows to create a new reagent.
     * @param reagentDTO the reagent to create.
     * @return the reagent created.
     */
    @PostMapping
    public ResponseEntity<ReagentDTO> createReagent(@RequestBody @Valid ReagentDTO reagentDTO) {
        LOGGER.info("Try to create new Reagent Controller layer");
        ReagentDTO reagentCreated = reagentService.addReagent(reagentDTO);
        return new ResponseEntity<>(reagentCreated, HttpStatus.CREATED);
    }

    /**
     * This method allows to update a reagent.
     * @param id the id of the reagent to update.
     * @param reagentDTO the reagent to update.
     * @return the reagent updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReagent(@PathVariable long id, @RequestBody @Valid ReagentDTO reagentDTO) {
        LOGGER.info("Try to update a specific Reagent Controller layer");
        ReagentDTO reagentUpdated = reagentService.updateReagent(reagentDTO, id);
        return new ResponseEntity<>(reagentUpdated, HttpStatus.OK);
    }

    /**
     * This method allows to delete a reagent.
     * @param id the id of the reagent to delete.
     * @return a message that the reagent is deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReagent(@PathVariable long id) {
        LOGGER.info("Try to delete Reagent Controller layer");
        String reagentDeleted = reagentService.deleteReagent(id);
        return new ResponseEntity<>(reagentDeleted, HttpStatus.OK);
    }
}
