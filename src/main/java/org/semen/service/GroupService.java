package org.semen.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.semen.entities.Group;
import org.semen.entities.User;
import org.semen.repo.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GroupService {
    @Autowired
    private final GroupRepo groupRepo;

    private final UserService userService;
    private final AccountService accountService;

    @Transactional
    public @NonNull Group save(@NonNull Group group){
        return groupRepo.save(group);
    }

    public @NonNull Group create(@NonNull Group group, @NonNull Long creatorId){
        if (groupRepo.existsById(group.getId())){
            throw new EntityExistsException("Группа с таким ID " + group.getId() + " уже существует");
        }
        User creator = userService.getById(creatorId);

        group.setDateOfCreation(LocalDate.now());
        group.setCreator(creator);
        group.getMembers().add(creator);
        group.setAccount(accountService.create(group));
        return save(group);
    }

    public void deleteByCreatorAndId(@NonNull User creator, @NonNull Long groupId){
        groupRepo.deleteByCreatorAndId(creator, groupId);
    }

    public Group getById(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Группа с таким ID " + groupId + " не найдена"));
    }

    public void addMemberToGroup(@NonNull Long groupId, @NonNull Long userId, @NonNull Long requesterId){
        Group group = getById(groupId);
        checkCreator(group, requesterId);

        User newMember = userService.getById(userId);
        group.getMembers().add(newMember);
        save(group);
    }

    public void removeMemberFromGroup(@NonNull Long groupId, @NonNull Long userId, @NonNull Long requesterId){
        Group group = getById(groupId);
        checkCreator(group, requesterId);

        User oldMember = userService.getById(userId);
        group.getMembers().remove(oldMember);
        save(group);
    }

    public @NonNull Group update(@NonNull Long groupId, @NonNull Group newData, @NonNull Long requesterId){
        Group group = getById(groupId);
        checkCreator(group, requesterId);

        group.setName(newData.getName());
        return save(group);
    }

    private void checkCreator(Group group, Long requesterId){
        if (!group.getCreator().getId().equals(requesterId)){
            throw new SecurityException("Только создатель группы может добавить пользователя в группу");
        }
    }
}
