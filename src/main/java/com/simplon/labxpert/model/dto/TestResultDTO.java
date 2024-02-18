package com.simplon.labxpert.model.dto;

import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.entity.Test;
import com.simplon.labxpert.model.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
/**
 * DTO for the TestResult entity.
 * It contains all the fields that a testResult can have.
 */
@Getter
@Setter
public class TestResultDTO {

    private long testResultID;

    @NotNull(message = "the value of the test is mandatory")
    private double valueOfTest;

    @NotNull(message = "Test is mandatory")
    private TestDTO testDTO;

    @NotNull(message = "analysis is mandatory")
    private AnalysisDTO analysisDTO;
}
