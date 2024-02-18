package com.simplon.labxpert.controller;

import com.simplon.labxpert.model.dto.SchedulingDTO;
import com.simplon.labxpert.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing scheduling.
 *
 * @Author Ayoub Ait Si Ahmad
 */
@RestController
@RequestMapping("/api/v1/schedulings")
public class SchedulingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final SchedulingService schedulingService;

    @Autowired
    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    /**
     * This method allows to get all schedulings.
     *
     * @return a list of all schedulings.
     */
    @GetMapping
    public ResponseEntity<List<SchedulingDTO>> getALlSchedulings() {
        LOGGER.info("Fetching all schedulings Controller");
        List<SchedulingDTO> schedulingDTOS = schedulingService.getAllSchedulings();
        return new ResponseEntity<>(schedulingDTOS, HttpStatus.OK);
    }

    /**
     * This method allows to get a scheduling by id.
     *
     * @param id the id of the scheduling to get.
     * @return a scheduling.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDTO> getSchedulingById(@PathVariable long id) {
        LOGGER.info("Fetching Scheduling by id Controller");
        SchedulingDTO schedulingDTO = schedulingService.getSchedulingById(id);
        return new ResponseEntity<>(schedulingDTO, HttpStatus.OK);
    }

    /**
     * This method allows to create a scheduling.
     *
     * @param schedulingDTO the scheduling to create.
     * @return the created scheduling.
     */
    @PostMapping
    public ResponseEntity<SchedulingDTO> createScheduling(@Valid @RequestBody SchedulingDTO schedulingDTO) {
        LOGGER.info("Creating Scheduling Controller");
        SchedulingDTO schedulingDTO1 = schedulingService.createScheduling(schedulingDTO);
        return new ResponseEntity<>(schedulingDTO1, HttpStatus.CREATED);
    }

    /**
     * This method allows to delete a scheduling by id.
     *
     * @param id the id of the scheduling to delete.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteScheduling(@PathVariable long id) {
        LOGGER.info("Deleting Scheduling Controller");
        schedulingService.deleteScheduling(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
