package com.example.miniaspireapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author devenderchaudhary
 */
@Data
public class RepaymentRequest {
    private BigDecimal amount;
    private LocalDate repaymentDate;
}
