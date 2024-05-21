package com.example.miniaspireapi.dto;

import com.example.miniaspireapi.enums.RepaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author devenderchaudhary
 */
@Data
@Builder
@AllArgsConstructor
public class RepaymentResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate dueDate;
    private RepaymentStatus status;
}
