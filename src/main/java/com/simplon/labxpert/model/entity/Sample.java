package com.simplon.labxpert.model.entity;

import com.simplon.labxpert.model.enums.AnalysisType;
import com.simplon.labxpert.model.enums.SampleStatus;
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
@Table(name = "samples")
public class Sample {
    @Id
    @SequenceGenerator(
            name = "sample_id_sequence",
            sequenceName = "sample_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sample_id_sequence"
    )
    private long sampleID;

    @Column(name = "analysisType",nullable = false)
    @Enumerated(EnumType.STRING)
    private AnalysisType analysisType;

    @Column(name = "sampleDescription", nullable = false)
    private String sampleDescription;

    @Column(name = "collectionDate", nullable = false)
    private LocalDate collectionDate;

    @Enumerated(EnumType.STRING)
    private SampleStatus sampleStatus ;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(mappedBy = "sample", cascade = CascadeType.ALL, orphanRemoval = true)
    private Analysis analysis;
}
