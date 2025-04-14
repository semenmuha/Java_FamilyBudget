package org.semen.unit.services;

import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semen.entities.User;
import org.semen.repo.UserRepo;
import org.semen.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepo userRepo;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void createUser_ShouldReturnSavedUser() {
        User user = new User()
            .setLogin("testuser")
            .setEmail("test@example.com")
            .setName("Test User");
        
        when(userRepo.save(any(User.class))).thenReturn(user);
        
        User result = userService.create(user);
        
        assertNotNull(result);
        assertEquals("testuser", result.getLogin());
        verify(userRepo, times(1)).save(any(User.class));
    }
    
    @Test
    void createUser_WithExistingLogin_ShouldThrowException() {
        when(userRepo.existsByLogin("existing")).thenReturn(true);
        
        User user = new User().setLogin("existing");
        
        assertThrows(EntityExistsException.class, () -> userService.create(user));
    }
    
    @Test
    void getUserById_ShouldReturnUser() {
        User user = new User().setId(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        
        User result = userService.getById(1L);
        
        assertEquals(1L, result.getId());
    }
}