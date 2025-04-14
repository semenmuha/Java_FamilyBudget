package org.semen.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.semen.integration.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.semen.entities.User;
import org.semen.repo.UserRepo;
import org.semen.dto.request.UserCreateRequest;

class UserControllerIT extends AbstractIntegrationTest {
    
    @Autowired
    private UserRepo userRepo;
    
    @Test
    void createUser_ShouldReturn201() throws Exception {
        UserCreateRequest request = new UserCreateRequest()
            .setLogin("newuser")
            .setEmail("new@example.com")
            .setName("New User");
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login").value("newuser"));
    }
    
    @Test
    void getUser_ShouldReturn200() throws Exception {
        User user = userRepo.save(new User()
            .setLogin("testuser")
            .setEmail("test@example.com"));
        
        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }
}