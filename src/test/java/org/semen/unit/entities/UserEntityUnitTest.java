package org.semen.unit.entities;

import org.junit.jupiter.api.Test;
import org.semen.entities.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityUnitTest {

    @Test
    void testUserCreation() {
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        user.setName("John Doe");
        user.setDateOfRegistration(LocalDate.now());

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getLogin());
        assertEquals("test@example.com", user.getEmail());
        assertNotNull(user.getDateOfRegistration());
    }

    @Test
    void testEqualsAfterIdChange() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(1L);

        assertEquals(user1, user2); // До "удаления"

        user1.setId(null); // Имитируем "удаление" (ID становится null)

        assertNotEquals(user1, user2);
    }
}
