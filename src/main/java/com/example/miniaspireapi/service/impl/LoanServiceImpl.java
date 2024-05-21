package com.example.miniaspireapi.service.impl;

import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dao.Loan;
import com.example.miniaspireapi.dao.Repayment;
import com.example.miniaspireapi.dto.LoanRequest;
import com.example.miniaspireapi.dto.LoanResponse;
import com.example.miniaspireapi.dto.RepaymentResponse;
import com.example.miniaspireapi.enums.LoanStatus;
import com.example.miniaspireapi.enums.RepaymentStatus;
import com.example.miniaspireapi.exception.ResourceNotFoundException;
import com.example.miniaspireapi.repository.LoanRepository;
import com.example.miniaspireapi.repository.RepaymentRepository;
import com.example.miniaspireapi.service.LoanService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author devenderchaudhary
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Resource
    private LoanRepository loanRepository;
    @Resource
    private RepaymentRepository repaymentRepository;
    @Resource
    private UserServiceImpl userService;

    @Transactional
    public LoanResponse createLoan(LoanRequest loanRequest) {

        User user = userService.getCurrentAuthenticatedUser();

        Loan loan = generateLoanObject(loanRequest);
        loan.setUser(user);
        LocalDate startDate = loan.getStartDate();
        BigDecimal amount = loan.getAmount();
        int term = loan.getTerm();
        BigDecimal installment = amount.divide(BigDecimal.valueOf(term), RoundingMode.HALF_UP);

        for (int i = 0; i < term; i++) {
            Repayment repayment = new Repayment();
            repayment.setLoan(loan);
            repayment.setAmount(i == term - 1 ? installment.add(amount.remainder(BigDecimal.valueOf(term))) : installment);
            repayment.setDueDate(startDate.plusWeeks(i + 1));
            repayment.setStatus(RepaymentStatus.PENDING);
            loan.getRepayments().add(repayment);
        }

        loan = loanRepository.save(loan);
        return getLoanResponse(loan);
    }

    public List<LoanResponse> getLoans() {
        User user = userService.getCurrentAuthenticatedUser();
        List<Loan> loans = loanRepository.findByUserId(user.getId());
        return getLoanResponse(loans);
    }

    public LoanResponse getLoan(long id) {
        User user = userService.getCurrentAuthenticatedUser();
        Loan loans = loanRepository.findByUserIdAndId(user.getId(), id);
        return getLoanResponse(loans);
    }

    public void approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id " + loanId));

        if (loan.getLoanStatus() != LoanStatus.PENDING) {
            throw new IllegalStateException("Loan must be in PENDING state to be approved");
        }
        loan.setLoanStatus(LoanStatus.APPROVED);
        loanRepository.save(loan);
    }

    @Transactional
    public void addRepayment(Long loanId, BigDecimal amount, LocalDate repaymentDate) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        if(LoanStatus.PAID.equals(loan.getLoanStatus()))
            throw new ResourceNotFoundException("Loan is already paid!");
        if(!LoanStatus.APPROVED.equals(loan.getLoanStatus()))
            throw new ResourceNotFoundException("Loan is not approved yet!");

        List<Repayment> repayments = loan.getRepayments();
        for (Repayment repayment : repayments) {
            if (repayment.getStatus().equals(RepaymentStatus.PENDING) && amount.compareTo(repayment.getAmount()) >= 0) {
                repayment.setStatus(RepaymentStatus.PAID);
                repaymentRepository.save(repayment);
                break;
            }
        }

        if (repayments.stream().allMatch(r -> RepaymentStatus.PAID.equals(r.getStatus()))) {
            loan.setLoanStatus(LoanStatus.PAID);
            loanRepository.save(loan);
        }
    }

    private List<LoanResponse> getLoanResponse(List<Loan> loans) {
        if(CollectionUtils.isEmpty(loans))
            return new ArrayList<>();

        return loans.stream()
            .map(this::getLoanResponse)
            .collect(Collectors.toList());
    }

    private LoanResponse getLoanResponse(Loan loan) {
        return LoanResponse.builder()
            .id(loan.getId())
            .status(loan.getLoanStatus())
            .amount(loan.getAmount())
            .term(loan.getTerm())
            .startDate(loan.getStartDate())
            .repayments(getRepayments(loan.getRepayments()))
            .build();
    }

    private List<RepaymentResponse> getRepayments(List<Repayment> repayments) {
        if(CollectionUtils.isEmpty(repayments))
            return null;

        return repayments.stream()
            .map(repayment -> RepaymentResponse.builder()
                .id(repayment.getId())
                .dueDate(repayment.getDueDate())
                .amount(repayment.getAmount())
                .status(repayment.getStatus())
                .build())
            .collect(Collectors.toList());
    }

    private Loan generateLoanObject(LoanRequest loanRequest) {
        Loan loan = new Loan();
        BeanUtils.copyProperties(loanRequest, loan);
        loan.setLoanStatus(LoanStatus.PENDING);
        return  loan;
    }
}
