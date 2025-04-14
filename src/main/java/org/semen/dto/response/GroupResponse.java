package org.semen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Data
@Accessors(chain = true)
public class GroupResponse {
    @Schema(description = "ID группы", example = "1")
    private Long id;
    private String name;
    private Long creatorId;
    private LocalDate dateOfCreation;
    private Set<Long> memberIds;
    // getters + setters
}