package org.semen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GroupCreateRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Название группы", example = "Семейный бюджет", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}