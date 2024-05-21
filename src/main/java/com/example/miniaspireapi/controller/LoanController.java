package com.example.miniaspireapi.controller;

import com.example.miniaspireapi.dto.BaseResponse;
import com.example.miniaspireapi.dto.LoanRequest;
import com.example.miniaspireapi.dto.LoanResponse;
import com.example.miniaspireapi.dto.RepaymentRequest;
import com.example.miniaspireapi.service.impl.LoanServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author devenderchaudhary
 */
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Resource
    private LoanServiceImpl loanService;

    @PostMapping
    public ResponseEntity<BaseResponse<LoanResponse>> createLoan(@RequestBody LoanRequest loanRequest) {

        LoanResponse loanResponse = loanService.createLoan(loanRequest);
        return ResponseEntity.ok(new BaseResponse<>(loanResponse));
    }

    @PostMapping("/{loanId}/repayments")
    public ResponseEntity<?> addRepayment(@PathVariable Long loanId, @RequestBody RepaymentRequest repaymentRequest) {
        try {
            loanService.addRepayment(loanId, repaymentRequest.getAmount(), repaymentRequest.getRepaymentDate());
            return ResponseEntity.ok("Repayment added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
