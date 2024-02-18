package com.simplon.labxpert.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplon.labxpert.model.enums.AnalysisStatus;
import com.simplon.labxpert.model.enums.ResultStatus;
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
@Table(name = "analysis")
public class Analysis {
    @Id
    @SequenceGenerator(
            name = "analysis_id_sequence",
            sequenceName = "analysis_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "analysis_id_sequence"
    )
    private long analysisID;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "comments", nullable = false)
    private String comments;

    @Column(name = "analysisStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnalysisStatus analysisStatus ;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    @OneToOne
    @JoinColumn(name = "sample_id")
    private Sample sample;

    @OneToMany(mappedBy = "analysis")
    private List<AnalysisReagent> analysisReagents;

    @JsonIgnore
    @OneToMany(mappedBy = "analysis")
    private List<Scheduling> schedulings;


    @OneToMany(mappedBy = "analysis")
    private List<TestResult> testResults;
}
