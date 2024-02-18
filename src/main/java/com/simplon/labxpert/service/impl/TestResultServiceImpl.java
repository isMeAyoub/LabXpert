package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.controller.TestResultController;
import com.simplon.labxpert.mapper.TestResultMapper;
import com.simplon.labxpert.model.dto.TestResultDTO;
import com.simplon.labxpert.model.entity.TestResult;
import com.simplon.labxpert.repository.TestResultRepository;
import com.simplon.labxpert.service.TestResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TestResult service.
 * It contains the methods that the service will implement.
 */
@Service
public class TestResultServiceImpl implements TestResultService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestResultServiceImpl.class);

    private final TestResultRepository testResultRepository;
    private final TestResultMapper testResultMapper;

    @Autowired
    public TestResultServiceImpl(TestResultRepository testResultRepository, TestResultMapper testResultMapper) {
        this.testResultRepository = testResultRepository;
        this.testResultMapper = testResultMapper;
    }


    @Override
    public List<TestResultDTO> getAllTestResults() {
        LOGGER.info("Getting all test results");
        List<TestResult> testResultList = testResultRepository.findAll();
        return testResultList.stream().map(testResultMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TestResultDTO getTestResultById(long testResultId) {
        return null;
    }

    @Override
    public TestResultDTO createTestResult(TestResultDTO testResultDTO) {
        LOGGER.info("Creating test result");
        return null;
    }

    @Override
    public TestResultDTO updateTestResult(TestResultDTO testResultDTO, long id) {
        return null;
    }

    @Override
    public void deleteTestResult(long testResultId) {
    }
}
