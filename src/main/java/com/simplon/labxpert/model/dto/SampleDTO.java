package com.simplon.labxpert.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.enums.AnalysisType;
import com.simplon.labxpert.model.enums.SampleStatus;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
/**
 * DTO for the Sample entity.
 * It contains all the fields that a sample can have.
 */
@Getter
@Setter
public class SampleDTO {
    private long sampleID;

    @NotNull(message = "Analysis type cannot be null")
    private AnalysisType analysisType;

    @NotBlank(message = "Sample description cannot be blank")
    private String sampleDescription;

    @PastOrPresent(message="date of collection must be on the present or the past")
    private LocalDate collectionDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private SampleStatus sampleStatus;

    //  TODO : @ayoub ait si ahmad THIS SHOULD NOT DISPLAY THE IN SOME CASES
    @NotNull(message = "Patient is mandatory")
    private PatientDTO patientDTO;

    //  TODO : @ayoub ait si ahmad THIS SHOULD NOT DISPLAY THE IN SOME CASES
    private AnalysisDTO analysisDTO;
}
