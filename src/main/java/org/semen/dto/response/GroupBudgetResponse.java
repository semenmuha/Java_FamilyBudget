package org.semen.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GroupBudgetResponse {
    private GroupResponse group;
    private AccountResponse account;
    private List<TransactionResponse> transactions;
}