package org.semen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GroupMemberRequest {
    @NotNull
    @Schema(description = "ID пользователя", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
}