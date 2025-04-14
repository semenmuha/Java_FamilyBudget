package org.semen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransactionRequest {
    @NotNull
    @Positive
    @Schema(description = "Сумма транзакции", example = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;
    
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Описание транзакции", example = "Покупка продуктов", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}