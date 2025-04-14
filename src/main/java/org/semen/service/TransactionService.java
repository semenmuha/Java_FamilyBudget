package org.semen.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.semen.entities.Transaction;
import org.semen.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @Autowired
    private final TransactionRepo transactionRepo;

    public @NonNull Transaction save(@NonNull Transaction transaction){
        return transactionRepo.save(transaction);
    }

    public @NonNull Transaction create(@NonNull Transaction transaction) {
        transaction.setDate(LocalDateTime.now());

        return save(transaction);
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepo.findByAccountIdOrderByDateDesc(accountId);
    }
}
