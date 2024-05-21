package com.example.miniaspireapi.dao;

import com.example.miniaspireapi.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

/**
 * @author devenderchaudhary
 */
@Data
@ToString(exclude = "customer")
@Entity
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;
    private int term;
    private LocalDate startDate;

    @Enumerated(STRING)
    private LoanStatus loanStatus;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Repayment> repayments = new ArrayList<>();
}
