package com.simplon.labxpert.model.dto;

import com.simplon.labxpert.model.entity.TestResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for the Test entity.
 * It contains all the fields that a test can have.
 */
@Getter
@Setter
public class TestDTO {

    private long testID;
    private String nameTest;
    private double maxValue;
    private double minValue;
    private String measurementUnits;
}
