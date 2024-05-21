package com.example.miniaspireapi.controller;

import com.example.miniaspireapi.service.LoanService;
import com.example.miniaspireapi.service.impl.LoanServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author devenderchaudhary
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private LoanService loanService;

    @PutMapping("/loan/{id}/approve")
    public ResponseEntity<Void> approveLoan(@PathVariable Long id) {
        loanService.approveLoan(id);
        return ResponseEntity.ok().build();
    }
}
