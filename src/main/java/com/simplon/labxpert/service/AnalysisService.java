package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.AnalysisDTO;


import java.util.List;
/**
 * Interface of the Analysis service.
 * It contains the methods that the service will implement.
 */
public interface AnalysisService {
    List<AnalysisDTO> findAllAnalysis();
    AnalysisDTO findAnalysisById(Long id);
    AnalysisDTO createAnalysis(AnalysisDTO analysisDTO);
    AnalysisDTO updateAnalysis(AnalysisDTO analysisDTO);
    String deleteAnalysis(Long id);
    void startAnalysis(Long id);
}
