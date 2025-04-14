package org.semen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class AccountResponse {
    @Schema(description = "ID счета", example = "1")
    private Long id;
    private Long groupId;
    private BigDecimal balance;
}