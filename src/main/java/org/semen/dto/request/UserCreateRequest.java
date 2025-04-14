package org.semen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCreateRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Логин пользователя", example = "ivan_ivanov", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;
    
    @NotBlank @Email
    @Schema(description = "Email пользователя", example = "ivan@mail.ru", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @Size(min = 2, max = 100)
    @Schema(description = "ФИО пользователя", example = "Иванов Иван")
    private String name;
}