package com.simplon.labxpert.model.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "analysis_reagents")
public class AnalysisReagent {
    @Id
    @SequenceGenerator(
            name = "analysisReagent_id_sequence",
            sequenceName = "analysisReagent_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "analysisReagent_id_sequence"
    )
    private long analysisReagentID;

    @ManyToOne
    private Analysis analysis;

    @ManyToOne
    private Reagent reagent;
    @Column(name = "quantity", nullable = false)
    private int quantity;
}
