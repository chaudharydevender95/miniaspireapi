package com.example.miniaspireapi.service;

import com.example.miniaspireapi.dao.Loan;
import com.example.miniaspireapi.dao.User;
import com.example.miniaspireapi.dto.LoanRequest;
import com.example.miniaspireapi.dto.LoanResponse;
import com.example.miniaspireapi.repository.LoanRepository;
import com.example.miniaspireapi.service.impl.LoanServiceImpl;
import com.example.miniaspireapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author devenderchaudhary
 */
class LoanServiceTest {

    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private RepaymentRepository repaymentRepository;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateLoan_Success() {
        Loan loan = new Loan();
        loan.setAmount(BigDecimal.valueOf(1000));
        loan.setTerm(10);
        loan.setStartDate(LocalDate.now());

        LoanRequest loanRequest = new LoanRequest();
        BeanUtils.copyProperties(loan, loanRequest);

        User user = new User();
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanResponse result = loanService.createLoan(loanRequest);

        assertNotNull(result);
        assertEquals(loan.getAmount(), result.getAmount());
        assertEquals(loan.getTerm(), result.getTerm());
        assertEquals(loan.getStartDate(), result.getStartDate());
    }

    @Test
    public void testCreateLoan_NullLoan() {
        assertThrows(NullPointerException.class, () -> loanService.createLoan(null));
    }

    @Test
    public void testGetLoans_UserHasLoans() {
        User user = new User();
        user.setId(1L);
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan());
        when(loanRepository.findByUserId(user.getId())).thenReturn(loans);

        List<LoanResponse> result = loanService.getLoans();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetLoans_UserHasNoLoans() {
        User user = new User();
        user.setId(1L);
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        when(loanRepository.findByUserId(user.getId())).thenReturn(new ArrayList<>());

        List<LoanResponse> result = loanService.getLoans();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testApproveLoan_LoanExists() {
        Loan loan = new Loan();
        loan.setId(1L);
        when(loanRepository.findById(loan.getId())).thenReturn(java.util.Optional.of(loan));

        loanService.approveLoan(loan.getId());

        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testApproveLoan_LoanDoesNotExist() {
        Long loanId = 1L;
        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> loanService.approveLoan(loanId));
    }

    @Test
    public void testAddRepayment_LoanDoesNotExist() {
        Long loanId = 1L;
        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> loanService.addRepayment(loanId, BigDecimal.valueOf(100), LocalDate.now()));
    }
}