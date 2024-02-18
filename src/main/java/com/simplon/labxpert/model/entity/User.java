package com.simplon.labxpert.model.entity;
import com.simplon.labxpert.model.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(name = "userID")
    private long userID;
    @Column(name = "email", unique = true , nullable = false)
    private String email;
    @Column(name = "emailVerified" , nullable = false)
    private Boolean isEmailVerified;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @Column(name = "personalInfo")
    private String personalInfo;
    @OneToMany(mappedBy = "user")
    private List<Scheduling> schedulings;
}
