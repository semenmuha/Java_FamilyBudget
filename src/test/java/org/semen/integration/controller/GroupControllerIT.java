package org.semen.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semen.integration.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.semen.entities.User;
import org.semen.entities.Group;
import org.semen.repo.UserRepo;
import org.semen.repo.GroupRepo;
import org.semen.dto.request.GroupCreateRequest;

class GroupControllerIT extends AbstractIntegrationTest {
    
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GroupRepo groupRepo;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = userRepo.save(new User()
            .setLogin("testuser")
            .setEmail("test@example.com"));
    }
    
    @Test
    void createGroup_ShouldReturn201() throws Exception {
        GroupCreateRequest request = new GroupCreateRequest()
            .setName("Test Group");
        
        mockMvc.perform(post("/api/groups")
                .header("X-User-Id", testUser.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Group"));
    }
    
    @Test
    void getGroupBudget_ShouldReturn200() throws Exception {
        Group group = groupRepo.save(new Group()
            .setName("Test Group")
            .setCreator(testUser));
        
        mockMvc.perform(get("/api/groups/{groupId}/budget", group.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group.id").value(group.getId()));
    }
}