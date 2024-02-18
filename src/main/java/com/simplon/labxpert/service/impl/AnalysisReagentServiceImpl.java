package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.AnalysisReagentMapper;
import com.simplon.labxpert.model.dto.AnalysisReagentDTO;
import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.entity.AnalysisReagent;
import com.simplon.labxpert.model.entity.Reagent;
import com.simplon.labxpert.model.enums.AnalysisStatus;
import com.simplon.labxpert.model.enums.ReagentStatus;
import com.simplon.labxpert.repository.AnalysisReagentRepository;
import com.simplon.labxpert.repository.AnalysisRepository;
import com.simplon.labxpert.repository.ReagentRepository;
import com.simplon.labxpert.service.AnalysisReagentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of the AnalysisReagent service.
 * It contains the methods that the service will implement.
 */
@Service
public class AnalysisReagentServiceImpl implements AnalysisReagentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisReagentServiceImpl.class);

    private final AnalysisReagentRepository analysisReagentRepository;
    private final AnalysisRepository analysisRepository;
    private final ReagentRepository reagentRepository;
    private final AnalysisReagentMapper analysisReagentMapper;

    @Autowired
    public AnalysisReagentServiceImpl(AnalysisReagentRepository analysisReagentRepository,
                                      AnalysisReagentMapper analysisReagentMapper,
                                      AnalysisRepository analysisRepository,
                                      ReagentRepository reagentRepository
    ) {
        this.analysisReagentRepository = analysisReagentRepository;
        this.analysisReagentMapper = analysisReagentMapper;
        this.analysisRepository = analysisRepository;
        this.reagentRepository = reagentRepository;
    }

    @Override
    public AnalysisReagentDTO createAnalysisReagent(AnalysisReagentDTO analysisReagentDTO) {
        LOGGER.info("Creating analysis reagent");
        AnalysisReagent analysisReagent = analysisReagentMapper.toEntity(analysisReagentDTO);

        Analysis analysis = analysisRepository.findById(analysisReagentDTO.getAnalysisDTO().getAnalysisID())
                .orElseThrow(() -> new CustomNotFoundException("Analysis not found with id : " + analysisReagentDTO.getAnalysisDTO().getAnalysisID(), HttpStatus.NOT_FOUND));

        Reagent reagent = reagentRepository.findById(analysisReagentDTO.getReagentDTO().getReagentID())
                .orElseThrow(() -> new CustomNotFoundException("Reagent not found with id : " + analysisReagentDTO.getReagentDTO().getReagentID(), HttpStatus.NOT_FOUND));

        if (!analysis.getAnalysisStatus().equals(AnalysisStatus.NEED_SCHEDULING)) {
            LOGGER.error("Analysis status is not NEED_SCHEDULING");
            throw new CustomNotFoundException("Analysis status is not NEED_SCHEDULING", HttpStatus.NOT_FOUND);
        }

        if (!reagent.getReagentStatus().equals(ReagentStatus.IN_STOCK_VALID)) {
            LOGGER.error("Reagent status is not IN_STOCK_VALID");
            throw new CustomNotFoundException("Reagent status is not IN_STOCK_VALID", HttpStatus.NOT_FOUND);
        }

        if (reagent.getQuantityInStock() < analysisReagentDTO.getQuantity()) {
            LOGGER.error("Not enough reagent");
            throw new CustomNotFoundException("Not enough reagent", HttpStatus.NOT_FOUND);
        }

        reagent.setQuantityInStock(reagent.getQuantityInStock() - analysisReagentDTO.getQuantity());
        reagentRepository.save(reagent);

        analysisReagent.setAnalysis(analysis);
        analysisReagent.setReagent(reagent);
        analysisReagentRepository.save(analysisReagent);

        return analysisReagentMapper.toDTO(analysisReagent);
    }

    @Override
    public List<AnalysisReagentDTO> getAllAnalysisReagents() {
        LOGGER.info("Getting all analysis reagents");
        List<AnalysisReagent> analysisReagents = analysisReagentRepository.findAll();
        // map the list of analysis reagents to a list of analysis reagents DTO
        return analysisReagents.stream().map(analysisReagentMapper::toDTO).collect(Collectors.toList());
    }
}
