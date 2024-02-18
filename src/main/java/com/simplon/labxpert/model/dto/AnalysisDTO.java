package com.simplon.labxpert.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.labxpert.model.enums.AnalysisStatus;
import com.simplon.labxpert.model.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
/**
 * DTO for the Analysis entity.
 * It contains all the fields that we need to send back and forth.
 */
@Getter
@Setter
public class AnalysisDTO {
    private long analysisID;

    @FutureOrPresent(message = "Start date must be in the present or in the future")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date must be in the present or in the future")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Comments is required")
    private String comments;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AnalysisStatus analysisStatus;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ResultStatus resultStatus;

    @NotNull(message = "Sample is required")
    private SampleDTO sampleDTO;

    @NotNull(message = "Analysis reagent is required")
    private List<AnalysisReagentDTO> analysisReagentDTOs;

    @NotNull(message = "testResults is required")
    private List<TestResultDTO> testResultDTOs;
}
