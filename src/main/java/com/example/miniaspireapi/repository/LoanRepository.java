package com.example.miniaspireapi.repository;

import com.example.miniaspireapi.dao.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author devenderchaudhary
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long customerId);
    Loan findByUserIdAndId(Long userId, Long id);
}
