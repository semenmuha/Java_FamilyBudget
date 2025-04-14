package org.semen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.semen.dto.request.GroupCreateRequest;
import org.semen.dto.request.GroupMemberRequest;
import org.semen.dto.response.AccountResponse;
import org.semen.dto.response.GroupBudgetResponse;
import org.semen.dto.response.GroupResponse;
import org.semen.mappers.GroupMapper;
import org.semen.mappers.TransactionMapper;
import org.semen.service.AccountService;
import org.semen.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "Group Management")
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private final GroupService service;
    @Autowired
    private final GroupMapper mapper;

    @Autowired
    private final TransactionMapper transactionMapper;
    @Autowired
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Создать группу")
    @ApiResponse(responseCode = "201", description = "Группа создана")
    public ResponseEntity<GroupResponse> create(
            @RequestHeader("X-User-Id") Long creatorId,
            @Valid @RequestBody GroupCreateRequest request) {
        
        var group = service.create(mapper.toEntity(request), creatorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(group));
    }
    
    @PostMapping("/{groupId}/members")
    @Operation(summary = "Добавить участника в группу")
    public ResponseEntity<String> addMember(
            @PathVariable Long groupId,
            @RequestHeader("X-User-Id") Long requesterId,
            @Valid @RequestBody GroupMemberRequest request) {
        
        service.addMemberToGroup(groupId, request.getUserId(), requesterId);
        return ResponseEntity.status(HttpStatus.OK).body("Добавление успешно");
    }
    
    @GetMapping("/{groupId}/budget")
    @Operation(summary = "Получить бюджет группы")
    public ResponseEntity<GroupBudgetResponse> getBudget(@PathVariable Long groupId) {
        var group = service.getById(groupId);
        var account = group.getAccount();
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GroupBudgetResponse()
                        .setGroup(mapper.toResponse(group))
                        .setAccount(new AccountResponse()
                                .setId(account.getId())
                                .setGroupId(groupId)
                                .setBalance(account.getBalance()))
                        .setTransactions(accountService.getTransactionHistory(groupId).stream()
                                .map(transactionMapper::toResponse)
                                .toList())
                );
    }
}