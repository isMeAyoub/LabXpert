package com.simplon.labxpert.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statisticalReports")
public class StatisticalReport {
    @Id
    @SequenceGenerator(
            name = "statisticalReport_id_sequence",
            sequenceName = "statisticalReport_id_sequence",
            allocationSize = 1
    )
    @javax.persistence.GeneratedValue(
            strategy = javax.persistence.GenerationType.SEQUENCE,
            generator = "statisticalReport_id_sequence"
    )
    private long statisticalReportID;
    @Column(name = "creationDate", nullable = false)
    private Timestamp creationDate;
    @Column(name = "createdBy", nullable = false)
    private String createdBy;
    @Column(name = "reportName", nullable = false)
    private String reportName;
    @Column(name = "reportType", nullable = false)
    private String reportType;
    @Column(name = "reportPeriod", nullable = false)
    private LocalDate reportPeriod;

}
