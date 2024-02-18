package com.simplon.labxpert.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @SequenceGenerator(
            name = "test_id_sequence",
            sequenceName = "test_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "test_id_sequence"
    )
    private long testID;
    @Column(name = "nameTest", nullable = false)
    private String nameTest;
    @Column(name = "maxValue", nullable = false)
    private double maxValue;
    @Column(name = "minValue", nullable = false)
    private double minValue;
    @Column(name = "measurementUnits", nullable = false)
    private String measurementUnits;

    @OneToMany(mappedBy = "test")
    private List<TestResult> testResult;

}
