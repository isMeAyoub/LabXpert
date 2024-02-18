package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.SchedulingMapper;
import com.simplon.labxpert.model.dto.SchedulingDTO;
import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.entity.Scheduling;
import com.simplon.labxpert.model.entity.User;
import com.simplon.labxpert.model.enums.AnalysisStatus;
import com.simplon.labxpert.repository.AnalysisRepository;
import com.simplon.labxpert.repository.SchedulingRepository;
import com.simplon.labxpert.repository.UserRepository;
import com.simplon.labxpert.service.SchedulingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of the scheduling service.
 * It contains the methods that the service will implement.
 */
@Service
public class SchedulingServiceImpl implements SchedulingService {
    private final static String SCHEDULING_NOT_FOUND = "Scheduling not found with id: ";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final SchedulingMapper schedulingMapper;
    private final SchedulingRepository schedulingRepository;
    private final UserRepository userRepository;
    private final AnalysisRepository analysisRepository;

    @Autowired
    public SchedulingServiceImpl(SchedulingMapper schedulingMapper,
                                 SchedulingRepository schedulingRepository,
                                 UserRepository userRepository,
                                 AnalysisRepository analysisRepository) {
        this.schedulingMapper = schedulingMapper;
        this.schedulingRepository = schedulingRepository;
        this.userRepository = userRepository;
        this.analysisRepository = analysisRepository;
    }


    @Override
    public SchedulingDTO getSchedulingById(long schedulingId) {
        try {
            LOGGER.info("Fetching schudeling by ID: {}",schedulingId);
            Optional<Scheduling> schedulingOptional = schedulingRepository.findById(schedulingId);
            if (!schedulingOptional.isPresent()){
                LOGGER.warn(SCHEDULING_NOT_FOUND + schedulingId);
                throw new CustomNotFoundException(SCHEDULING_NOT_FOUND + schedulingId, HttpStatus.NOT_FOUND);
            } else {
                return schedulingMapper.toDTO(schedulingOptional.get());
            }
        } catch (Exception e) {
            LOGGER.error("Eroor occurred while feching scheduling with ID: {}",schedulingId,e);
            throw e;
        }
    }

    @Override
    public List<SchedulingDTO> getAllSchedulings() {
        try {
            LOGGER.info("Fetching all scheduling");
            List<Scheduling> schedulings = schedulingRepository.findAll();
            List<SchedulingDTO> schedulingDTOS = schedulings.stream().map(schedulingMapper::toDTO).collect(Collectors.toList());
            return schedulingDTOS;
        } catch (Exception exception) {
            LOGGER.error("Error occured while fetching all scheduling");
            throw exception;
        }
    }

    @Override
    public SchedulingDTO createScheduling(SchedulingDTO schedulingDTO) {
        LOGGER.info("Creating scheduling");
        Scheduling scheduling = schedulingMapper.toEntity(schedulingDTO);
        Optional<User> userOptional = userRepository.findById(schedulingDTO.getUserDTO().getUserID());
        if (!userOptional.isPresent()){
            LOGGER.warn("User not found with id: {}",schedulingDTO.getUserDTO().getUserID());
            throw new CustomNotFoundException("User not found with id: "+schedulingDTO.getUserDTO().getUserID(), HttpStatus.NOT_FOUND);
        }
        Optional<Analysis> analysisOptional = analysisRepository.findById(schedulingDTO.getAnalysisDTO().getAnalysisID());
        if (!analysisOptional.isPresent()){
            LOGGER.warn("Analysis not found with id: {}",schedulingDTO.getAnalysisDTO().getAnalysisID());
            throw new CustomNotFoundException("Analysis not found with id: "+schedulingDTO.getAnalysisDTO().getAnalysisID(), HttpStatus.NOT_FOUND);
        }
        scheduling.setUser(userOptional.get());
        scheduling.setAnalysis(analysisOptional.get());
        // change the status of analysis to scheduled
        analysisOptional.get().setAnalysisStatus(AnalysisStatus.SCHEDULED);
        Analysis analysis = analysisRepository.save(analysisOptional.get());
        Scheduling schedulingCreated = schedulingRepository.save(scheduling);
        return schedulingMapper.toDTO(schedulingCreated);
    }

    @Override
    public SchedulingDTO updateScheduling(long schedulingId, SchedulingDTO schedulingDTO) {
        return null;
    }

    @Override
    public void deleteScheduling(long schedulingId) {
        LOGGER.info("Deleting scheduling with id: {}",schedulingId);
        Optional<Scheduling> schedulingOptional = schedulingRepository.findById(schedulingId);
        if (!schedulingOptional.isPresent()){
            LOGGER.warn(SCHEDULING_NOT_FOUND + schedulingId);
            throw new CustomNotFoundException(SCHEDULING_NOT_FOUND + schedulingId, HttpStatus.NOT_FOUND);
        }
        schedulingRepository.deleteById(schedulingId);
    }
}
