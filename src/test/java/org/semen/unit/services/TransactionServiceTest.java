package org.semen.unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semen.entities.Transaction;
import org.semen.repo.TransactionRepo;
import org.semen.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    
    @Mock
    private TransactionRepo transactionRepo;
    
    @InjectMocks
    private TransactionService transactionService;
    
    @Test
    void createTransaction_ShouldSetCurrentDate() {
        Transaction transaction = new Transaction()
            .setDescription("Test")
            .setAmount(BigDecimal.TEN);
        
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        
        Transaction result = transactionService.create(transaction);
        
        assertNotNull(result.getDate());
        verify(transactionRepo, times(1)).save(transaction);
    }
    
    @Test
    void getTransactionsByAccount_ShouldReturnList() {
        when(transactionRepo.findByAccountIdOrderByDateDesc(1L))
            .thenReturn(List.of(new Transaction().setId(1L)));
        
        List<Transaction> result = transactionService.getTransactionsByAccount(1L);
        
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}