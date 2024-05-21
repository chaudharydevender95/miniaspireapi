package com.example.miniaspireapi.service;

import com.example.miniaspireapi.dto.LoanRequest;
import com.example.miniaspireapi.dto.LoanResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author devenderchaudhary
 */
public interface LoanService {
    public void approveLoan(Long loanId);
    LoanResponse createLoan(LoanRequest loanRequest);
    List<LoanResponse> getLoans();
    void addRepayment(Long loanId, BigDecimal amount, LocalDate repaymentDate);
}
