package com.simplon.labxpert.model.dto;

import com.simplon.labxpert.model.entity.Sample;
import com.simplon.labxpert.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
/**
 * DTO for the Patient entity.
 * It contains all the fields that a patient can have.
 */
@Getter
@Setter
public class PatientDTO {
    // TODO : TO @ayoub ait si ahmad CHECK IF THE VALIDATION WORKS AS EXPECTED

    private long patientID;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String patientEmail;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotNull(message = "gender is required")
    private Gender gender;
    @NotBlank(message = "Address is required")
    private String address;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    // TODO : @ayoub ait si ahmad THIS SHOULD NOT BE DISPLAY IN THE SAMPLE RESPONSE
    private List<SampleDTO> samplesDTO;
}