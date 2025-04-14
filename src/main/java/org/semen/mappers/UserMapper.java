package org.semen.mappers;

import org.semen.dto.request.UserCreateRequest;
import org.semen.dto.request.UserUpdateRequest;
import org.semen.dto.response.UserResponse;
import org.semen.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateRequest request) {
        return new User()
                .setLogin(request.getLogin())
                .setEmail(request.getEmail())
                .setName(request.getName());
    }
    
    public User updateEntity(UserUpdateRequest request) {
        return new User()
                .setLogin(request.getLogin())
                .setEmail(request.getEmail())
                .setName(request.getName());
    }
    
    public UserResponse toResponse(User user) {
        return new UserResponse()
            .setId(user.getId())
            .setLogin(user.getLogin())
            .setEmail(user.getEmail())
            .setName(user.getName())
            .setDateOfRegistration(user.getDateOfRegistration());
    }
}
