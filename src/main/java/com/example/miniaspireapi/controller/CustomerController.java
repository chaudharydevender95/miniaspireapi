package com.example.miniaspireapi.controller;

import com.example.miniaspireapi.dto.LoanResponse;
import com.example.miniaspireapi.service.impl.LoanServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author devenderchaudhary
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Resource
    private LoanServiceImpl loanService;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanResponse>> getLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable("id") long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }


}
