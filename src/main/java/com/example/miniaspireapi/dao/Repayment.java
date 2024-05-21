package com.example.miniaspireapi.dao;

import com.example.miniaspireapi.enums.RepaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

/**
 * @author devenderchaudhary
 */
@Data
@ToString(exclude = "loan")
@Entity
public class Repayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private BigDecimal amount;
    private LocalDate dueDate;

    @Enumerated(STRING)
    private RepaymentStatus status;
}
