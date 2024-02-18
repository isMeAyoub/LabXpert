package com.simplon.labxpert.model.dto;

import com.simplon.labxpert.model.entity.Analysis;
import com.simplon.labxpert.model.entity.User;
import com.simplon.labxpert.model.enums.Priority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
/**
 * DTO for the Scheduling entity.
 * It contains all the fields that a scheduling can have.
 */
@Getter
@Setter
public class SchedulingDTO {
    private long schedulingID;

    @NotNull(message = "startDateAndTime must not be null")
    @Future(message = "startDateAndTime must be in the future")
    private LocalDate startDateAndTime;

    @NotNull(message = "endDateAndTime must not be null")
    @Future(message = "endDateAndTime must be in the future")
    private LocalDate endDateAndTime;

    @NotNull(message = "priority must not be null")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotBlank(message = "notes must not be blank")
    private String notes;

    @NotNull(message = "user must not be null")
    private UserDTO userDTO;

    @NotNull(message = "analysis must not be null")
    private AnalysisDTO analysisDTO;
}