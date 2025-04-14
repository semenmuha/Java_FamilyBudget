package org.semen.unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.semen.entities.Account;
import org.semen.entities.Group;
import org.semen.entities.User;
import org.semen.repo.GroupRepo;
import org.semen.service.AccountService;
import org.semen.service.GroupService;
import org.semen.service.UserService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    
    @Mock
    private GroupRepo groupRepo;
    @Mock
    private UserService userService;
    @Mock
    private AccountService accountService;
    
    @InjectMocks
    private GroupService groupService;

    @Test
    void createGroup_ShouldAddCreatorAsMember() {
        // 1. Подготовка тестовых данных
        Long creatorId = 1L;
        User mockCreator = new User()
                .setId(creatorId)
                .setLogin("creator")
                .setEmail("creator@example.com");

        Group inputGroup = new Group()
                .setName("Test Group");

        Account mockAccount = new Account()
                .setId(1L)
                .setBalance(BigDecimal.ZERO);

        // 2. Настройка моков
        when(userService.getById(creatorId)).thenReturn(mockCreator);
        when(accountService.create(any(Group.class))).thenReturn(mockAccount);
        when(groupRepo.save(any(Group.class))).thenAnswer(invocation -> {
            Group savedGroup = invocation.getArgument(0);
            savedGroup.setId(1L); // Эмулируем присвоение ID
            return savedGroup;
        });

        // 3. Вызов тестируемого метода
        Group result = groupService.create(inputGroup, creatorId);

        // 4. Проверки
        assertNotNull(result, "Группа не должна быть null");
        assertNotNull(result.getCreator(), "Создатель не должен быть null");
        assertEquals(mockCreator, result.getCreator(), "Создатель группы не совпадает");
        assertTrue(result.getMembers().contains(mockCreator),
                "Создатель должен быть в списке участников");
        assertNotNull(result.getDateOfCreation(), "Дата создания должна быть установлена");
        assertNotNull(result.getAccount(), "Счет должен быть создан");
    }
    
    @Test
    void addMember_ShouldAddUserToGroup() {
        Group group = new Group().setCreator(new User().setId(1L));
        User newMember = new User().setId(2L);
        
        when(groupRepo.findById(1L)).thenReturn(Optional.of(group));
        when(userService.getById(2L)).thenReturn(newMember);
        when(groupRepo.save(any(Group.class))).thenAnswer(inv -> inv.getArgument(0));
        
        groupService.addMemberToGroup(1L, 2L, 1L);
        
        assertTrue(group.getMembers().contains(newMember));
    }
}