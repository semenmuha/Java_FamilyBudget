package org.semen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.semen.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class TransactionResponse {
    @Schema(description = "ID транзакции", example = "1")
    private Long id;
    private String description;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime date;
}