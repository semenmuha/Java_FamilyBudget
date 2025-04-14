package org.semen.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "group_id", nullable = false, unique = true)
    private Group group;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}