package org.semen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.semen.dto.request.UserCreateRequest;
import org.semen.dto.request.UserUpdateRequest;
import org.semen.dto.response.UserResponse;
import org.semen.mappers.UserMapper;
import org.semen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService service;
    @Autowired
    private final UserMapper mapper;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.toResponse(
                        service.create(mapper.toEntity(request))
                )
        );
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toResponse(service.getById(id))
        );
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                mapper.toResponse(
                        service.update(id, mapper.updateEntity(request))
                )
        );
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Успешно удален");
    }
}