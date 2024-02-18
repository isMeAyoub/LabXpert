package com.simplon.labxpert.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplon.labxpert.model.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @SequenceGenerator(
            name = "patient_id_sequence",
            sequenceName = "patient_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_id_sequence"
    )
    private long patientID;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "patientEmail", unique = true, nullable = false)
    private String patientEmail;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name="dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sample> samples;
    //TODO :TO @chaimaa mahy ADD THE HISTORY OF THE ANALYSIS
}
