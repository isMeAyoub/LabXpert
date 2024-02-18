package com.simplon.labxpert.model.entity;

import com.simplon.labxpert.model.enums.ResultStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_results")
public class TestResult {
    @Id
    @SequenceGenerator(
            name = "testResult_id_sequence",
            sequenceName = "testResult_id_sequence",
            allocationSize = 1
    )
    @javax.persistence.GeneratedValue(
            strategy = javax.persistence.GenerationType.SEQUENCE,
            generator = "testResult_id_sequence"
    )
    private long testResultID;

    @Column(name = "valueOfTest", nullable = false)
    private double valueOfTest;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;

}
