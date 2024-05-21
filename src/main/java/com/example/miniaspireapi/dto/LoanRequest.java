package com.example.miniaspireapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author devenderchaudhary
 */
@Data
public class LoanRequest {
    private BigDecimal amount;
    private int term;
    private LocalDate startDate;
}
