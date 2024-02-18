package com.simplon.labxpert.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_id_sequence",
            sequenceName = "role_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_id_sequence"
    )
    private long roleID;
    @Column(name = "roleName", unique = true, nullable = false)
    private String roleName;
}
