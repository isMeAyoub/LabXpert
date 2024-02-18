package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.AnalysisReagentDTO;

import java.util.List;
/**
 * Interface of the AnalysisReagent service.
 * It contains the methods that the service will implement.
 */
public interface AnalysisReagentService {
    AnalysisReagentDTO createAnalysisReagent(AnalysisReagentDTO analysisReagentDTO);
    List<AnalysisReagentDTO> getAllAnalysisReagents();
}
