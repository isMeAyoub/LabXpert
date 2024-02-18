package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.AnalysisMapper;
import com.simplon.labxpert.model.dto.AnalysisDTO;
import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.entity.Sample;
import com.simplon.labxpert.model.entity.Test;
import com.simplon.labxpert.model.entity.TestResult;
import com.simplon.labxpert.model.enums.AnalysisStatus;
import com.simplon.labxpert.model.enums.ResultStatus;
import com.simplon.labxpert.repository.AnalysisRepository;
import com.simplon.labxpert.repository.SampleRepository;
import com.simplon.labxpert.repository.TestRepository;
import com.simplon.labxpert.repository.TestResultRepository;
import com.simplon.labxpert.service.AnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of the Analysis service.
 * It contains the methods that the service will implement.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;
    private final SampleRepository sampleRepository;
    private final AnalysisMapper analysisMapper;
    private final TestResultServiceImpl testResultService;
    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;

    @Autowired
    public AnalysisServiceImpl(AnalysisRepository analysisRepository,
                               AnalysisMapper analysisMapper,
                               SampleRepository sampleRepository,
                               TestResultServiceImpl testResultService,
                               TestRepository testRepository,
                               TestResultRepository testResultRepository) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
        this.sampleRepository = sampleRepository;
        this.testResultService = testResultService;
        this.testRepository = testRepository;
        this.testResultRepository = testResultRepository;
    }

    @Override
    public List<AnalysisDTO> findAllAnalysis() {
        LOGGER.info("Getting all analysis");
        List<Analysis> analysisList = analysisRepository.findAll();
        return analysisList.stream().map(analysisMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AnalysisDTO findAnalysisById(Long id) {
        LOGGER.info("Getting analysis with id : {}", id);
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Analysis not found with id : " + id, HttpStatus.NOT_FOUND));
        return analysisMapper.toDTO(analysis);
    }

    @Override
    public AnalysisDTO createAnalysis(AnalysisDTO analysisDTO) {
        LOGGER.info("Creating analysis");
        Analysis analysis = analysisMapper.toEntity(analysisDTO);
        Sample sample = getSample(analysisDTO.getSampleDTO().getSampleID());
        validateSample(sample);
        validateDates(analysis);
        analysis.setSample(sample);
        analysis.setAnalysisStatus(AnalysisStatus.NEED_SCHEDULING);
        analysis.setResultStatus(ResultStatus.WITHOUT_RESULT_YET);
        createTestResultsBasedOnAnalysisType(analysis);
        analysis = analysisRepository.save(analysis);
        LOGGER.info("Analysis created");
        return analysisMapper.toDTO(analysis);
    }

    @Override
    public AnalysisDTO updateAnalysis(AnalysisDTO analysisDTO) {
        return null;
    }

    public void startAnalysis(Long id) {
        LOGGER.info("Starting analysis with id : {}", id);
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Analysis not found with id : " + id, HttpStatus.NOT_FOUND));
        validateAnalysisStatus(analysis);
        analysis.setAnalysisStatus(AnalysisStatus.IN_ANALYSIS);
        analysisRepository.save(analysis);
        LOGGER.info("Analysis started");
    }

    @Override
    public String deleteAnalysis(Long id) {
        LOGGER.info("Deleting analysis with id : {}", id);
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Analysis not found with id : " + id, HttpStatus.NOT_FOUND));
        analysisRepository.delete(analysis);
        LOGGER.info("Analysis deleted");
        return "Analysis deleted successfully";
    }

    private Sample getSample(Long sampleId) {
        return sampleRepository.findById(sampleId)
                .orElseThrow(() -> new CustomNotFoundException("Sample not found with id : " + sampleId, HttpStatus.NOT_FOUND));
    }

    private void validateSample(Sample sample) {
        if (sample.getAnalysis() != null) {
            throw new CustomNotFoundException("Sample already analyzed", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDates(Analysis analysis) {
        if (analysis.getStartDate().isAfter(analysis.getEndDate())) {
            throw new CustomNotFoundException("Start date must be before the end date", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateAnalysisStatus(Analysis analysis) {
        if (analysis.getAnalysisStatus() != AnalysisStatus.SCHEDULED) {
            throw new CustomNotFoundException("Analysis not scheduled", HttpStatus.BAD_REQUEST);
        }
    }

    private void createTestResultsBasedOnAnalysisType(Analysis analysis) {
        switch (analysis.getSample().getAnalysisType()) {
            case BLOOD_CHEMISTRY:
                createBloodChemistryTestResults(analysis);
                break;
            case DNA_SEQUENCING:
                createDnaSequencingTestResults(analysis);
                break;
            case HORMONE_ASSAYS:
                createHormoneAssaysTestResults(analysis);
                break;
            case CYTOLOGY:
                createCytologyTestResults(analysis);
                break;
            default:
                throw new CustomNotFoundException("Analysis type not found", HttpStatus.BAD_REQUEST);
        }
    }

    private void createAndSaveTestResult(Analysis analysis, Test test) {
        TestResult testResult = new TestResult();
        testResult.setAnalysis(analysis);
        testResult.setTest(test);
        testResult.setValueOfTest(0);
        testResultRepository.save(testResult);
    }

    private void createBloodChemistryTestResults(Analysis analysis) {
        List<Test> testList = testRepository.findAll();
        for (int i = 0; i < 4; i++) {
            createAndSaveTestResult(analysis, testList.get(i));
        }
        analysis = analysisRepository.save(analysis);
    }

    private void createDnaSequencingTestResults(Analysis analysis) {
        List<Test> testList = testRepository.findAll();
        for (int i = 4; i < 7; i++) {
            createAndSaveTestResult(analysis, testList.get(i));
        }
        analysis = analysisRepository.save(analysis);
    }

    private void createHormoneAssaysTestResults(Analysis analysis) {
        List<Test> testList = testRepository.findAll();
        for (int i = 7; i < 10; i++) {
            createAndSaveTestResult(analysis, testList.get(i));
        }
        analysis = analysisRepository.save(analysis);
    }

    private void createCytologyTestResults(Analysis analysis) {
        List<Test> testList = testRepository.findAll();
        for (int i = 10; i < 13; i++) {
            createAndSaveTestResult(analysis, testList.get(i));
        }
        analysis = analysisRepository.save(analysis);
    }
}
