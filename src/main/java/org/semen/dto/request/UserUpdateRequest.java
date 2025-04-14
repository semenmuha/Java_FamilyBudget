package org.semen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserUpdateRequest {
    @Size(min = 3, max = 50)
    @Schema(description = "Логин пользователя", example = "new_ivanov")
    private String login;
    
    @Email
    @Schema(description = "Email пользователя", example = "new_ivan@mail.ru")
    private String email;
    
    @Size(min = 2, max = 100)
    @Schema(description = "ФИО пользователя", example = "Иванов Иван Петрович")
    private String name;
}