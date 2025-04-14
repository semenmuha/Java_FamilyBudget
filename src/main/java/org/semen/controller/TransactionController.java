package org.semen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.semen.dto.request.TransactionRequest;
import org.semen.dto.response.TransactionResponse;
import org.semen.mappers.TransactionMapper;
import org.semen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management")
@RequiredArgsConstructor
public class TransactionController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TransactionMapper mapper;

    @PostMapping("/deposit/{groupId}")
    @Operation(summary = "Пополнить бюджет группы")
    public ResponseEntity<TransactionResponse> deposit(
            @PathVariable Long groupId,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody TransactionRequest request) {
        
        var account = accountService.getAccountByGroupId(groupId);
        var transaction = mapper.toEntity(request, account);
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toResponse(accountService.deposit(groupId, transaction))
        );
    }

    @PostMapping("/withdrawal/{groupId}")
    @Operation(summary = "Снять из бюджета группы")
    public ResponseEntity<TransactionResponse> withdrawal(
            @PathVariable Long groupId,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody TransactionRequest request) {

        var account = accountService.getAccountByGroupId(groupId);
        var transaction = mapper.toEntity(request, account);
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toResponse(accountService.withdraw(groupId, transaction))
        );
    }
    
    @GetMapping("/account/{accountId}")
    @Operation(summary = "Получить историю транзакций по счету")
    public ResponseEntity<List<TransactionResponse>> getByAccount(@PathVariable Long accountId) {
        var account = accountService.getById(accountId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.getTransactionHistory(account.getGroup().getId()).stream()
                        .map(mapper::toResponse)
                        .toList());
    }
}