package org.semen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserResponse {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;
    private String login;
    private String email;
    private String name;
    private LocalDate dateOfRegistration;
}