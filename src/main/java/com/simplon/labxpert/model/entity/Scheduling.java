package com.simplon.labxpert.model.entity;

import com.simplon.labxpert.model.enums.Priority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedulings")
public class Scheduling {
    @Id
    @SequenceGenerator(
            name = "scheduling_id_sequence",
            sequenceName = "scheduling_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scheduling_id_sequence"
    )
    private long schedulingID;
    @Column(name = "startDateAndTime", nullable = false)
    private LocalDate startDateAndTime;
    @Column(name = "endDateAndTime", nullable = false)
    private LocalDate endDateAndTime;
    @Enumerated(EnumType.STRING)
    private Priority priority ;
    @Column(name = "notes", nullable = false)
    private String notes;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;
}
