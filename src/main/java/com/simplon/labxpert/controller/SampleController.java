package com.simplon.labxpert.controller;

import com.simplon.labxpert.model.dto.SampleDTO;
import com.simplon.labxpert.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is the controller of the sample entity
 * It allows to create, read, update and delete samples
 *
 * @Author @Ayoub Ait Si Ahmed and @Chaimaa Mahy
 */
@RestController
@RequestMapping("/api/v1/samples")
public class SampleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    private final SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    // TODO : To @chaimaa mahy PLASE DON'T MAKE THE TRY CATCH IN THE CONTROLLER FOR THE ALL ENDPOINTS IN THE APPLICATION I WILL FIX IT ME @ayoub ait si ahmad
    // BECAUSE THE CONTROLLER IS ONLY IS THE ROUTER OF THE APPLICATION
    // THE TRY CATCH MUST BE IN THE SERVICE LAYER AND THANK YOU

    /**
     * This method allows to get all samples
     *
     * @return a list of samples
     */
    @GetMapping
    public ResponseEntity<List<SampleDTO>> getAllSamples() {
        LOGGER.info("Fetching all simples");
        List<SampleDTO> sampleDTOS = sampleService.getAllSimple();
        return new ResponseEntity<>(sampleDTOS, HttpStatus.OK);
    }

    /**
     * This method allows to create a sample
     *
     * @param sampleDTO
     * @return the created sample
     */
    @PostMapping
    public ResponseEntity<SampleDTO> createSample(@Valid @RequestBody SampleDTO sampleDTO) {
        LOGGER.info("Creating sample");
        SampleDTO createdSample = sampleService.createSample(sampleDTO);
        return new ResponseEntity<>(createdSample, HttpStatus.OK);
    }

    /**
     * This method allows to get a sample by id
     *
     * @param sampleId
     * @return the sample
     */
    @GetMapping("/{sampleId}")
    public ResponseEntity<SampleDTO> getSampleByID(@PathVariable long sampleId) {
        SampleDTO sample = sampleService.getSampleById(sampleId);
        LOGGER.info("sample has been fetched successfully ");
        return new ResponseEntity<>(sample, HttpStatus.OK);
    }

    /**
     * This method allows to delete a sample by id
     *
     * @param sampleId
     * @return a message of success
     */
    @DeleteMapping("/{sampleId}")
    public ResponseEntity<String> deleteSample(@PathVariable long sampleId) {
        LOGGER.info("Deleting sample with id {}", sampleId);
        sampleService.deleteSample(sampleId);
        LOGGER.info("sample has been deleted successfully ");
        return new ResponseEntity<>("sample has been deleted successfully ", HttpStatus.OK);
    }
}
