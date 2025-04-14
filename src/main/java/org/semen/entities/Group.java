package org.semen.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    @ManyToMany(mappedBy = "groups")
    private Set<User> members = new HashSet<>();

    @OneToOne(mappedBy = "group")
    private Account account;
}
