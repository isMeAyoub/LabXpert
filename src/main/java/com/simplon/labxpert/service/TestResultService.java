package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.TestResultDTO;
import java.util.List;
/**
 * Interface of the TestResult service.
 * It contains the methods that the service will implement.
 */
public interface TestResultService {
   List<TestResultDTO> getAllTestResults();
   TestResultDTO getTestResultById(long  testResultId);
   TestResultDTO createTestResult(TestResultDTO testResultDTO);
   TestResultDTO updateTestResult(TestResultDTO testResultDTO , long id);
   void deleteTestResult(long testResultId);
   

}
