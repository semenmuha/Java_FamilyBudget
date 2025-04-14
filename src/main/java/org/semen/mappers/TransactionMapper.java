package org.semen.mappers;

import org.semen.dto.request.TransactionRequest;
import org.semen.dto.response.TransactionResponse;
import org.semen.entities.Account;
import org.semen.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toEntity(TransactionRequest request, Account account) {
        return new Transaction()
            .setDescription(request.getDescription())
            .setAmount(request.getAmount())
            .setAccount(account);
    }
    
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse()
            .setId(transaction.getId())
            .setDescription(transaction.getDescription())
            .setAccountId(transaction.getAccount().getId())
            .setAmount(transaction.getAmount())
            .setType(transaction.getTransactionType())
            .setDate(transaction.getDate());
    }
}