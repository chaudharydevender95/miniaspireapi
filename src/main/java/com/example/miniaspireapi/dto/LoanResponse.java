package com.example.miniaspireapi.dto;

import com.example.miniaspireapi.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author devenderchaudhary
 */
@Data
@Builder
@AllArgsConstructor
public class LoanResponse implements Serializable {
    private Long id;
    private BigDecimal amount;
    private int term;
    private LocalDate startDate;
    private LoanStatus status;
    private List<RepaymentResponse> repayments;
}
