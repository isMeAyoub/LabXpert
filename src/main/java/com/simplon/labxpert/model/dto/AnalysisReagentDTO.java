package com.simplon.labxpert.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO for the AnalysisReagent entity.
 * It contains all the fields that we need to send back and forth.
 */
@Getter
@Setter
public class AnalysisReagentDTO {

    private long analysisReagentID;

    @NotNull(message = "Analysis is required")
    @JsonProperty("analysisDTO")
    private AnalysisDTO analysisDTO;

    @NotNull(message = "Reagent is required")
    @JsonProperty("reagentDTO")
    private ReagentDTO reagentDTO;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @NotNull(message = "Quantity is required")
    private int quantity;

}
