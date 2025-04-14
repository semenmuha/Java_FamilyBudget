package org.semen.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.semen.entities.Account;
import org.semen.entities.Group;
import org.semen.entities.Transaction;
import org.semen.enums.TransactionType;
import org.semen.exceptions.InsufficientFundsException;
import org.semen.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private final AccountRepo accountRepo;

    private final TransactionService transactionService;

    @Transactional
    public @NonNull Account save(@NonNull Account account){
        return accountRepo.save(account);
    }

    public @NonNull Account create(@NonNull Group group){
        Account account = new Account();
        account.setGroup(group);
        account.setBalance(BigDecimal.ZERO);
        return save(account);
    }

    public @NonNull Account getById(@NonNull Long accountId){
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт с ID " + accountId + " не найден"));
    }

    public BigDecimal getCurrentBalance(Long groupId) {
        Account account = getAccountByGroupId(groupId);
        return account.getBalance();
    }

    public Transaction deposit(Long groupId, Transaction transaction) {
        Account account = getAccountByGroupId(groupId);
        checkEquals(account, transaction.getAccount());

        account.setBalance(account.getBalance().add(transaction.getAmount()));
        save(account);

        transaction.setTransactionType(TransactionType.DEPOSIT);
        return transactionService.create(transaction);
    }

    public Transaction withdraw(Long groupId, Transaction transaction) {
        Account account = getAccountByGroupId(groupId);
        checkEquals(account, transaction.getAccount());

        if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientFundsException("Недостаточно денег на счёте");
        }

        account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        save(account);

        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        return transactionService.create(transaction);
    }

    public List<Transaction> getTransactionHistory(Long groupId) {
        Account account = getAccountByGroupId(groupId);
        return transactionService.getTransactionsByAccount(account.getId());
    }

    public Account getAccountByGroupId(Long groupId) {
        return accountRepo.findByGroupId(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт с группы с ID " + groupId + " не найден"));
    }

    private void checkEquals(Account account1, Account account2){
        if (!account1.equals(account2)){
            throw new SecurityException("Счета разные");
        }
    }
}
