package org.semen.unit.entities;

import org.junit.jupiter.api.Test;
import org.semen.entities.Group;
import org.semen.entities.User;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GroupEntityUnitTest {

    @Test
    void testGroupCreation() {
        User creator = new User();
        creator.setId(1L);

        Group group = new Group();
        group.setId(10L);
        group.setCreator(creator);
        group.setDateOfCreation(LocalDate.now());

        assertEquals(10L, group.getId());
        assertEquals(1L, group.getCreator().getId());
        assertNotNull(group.getDateOfCreation());
    }
}
