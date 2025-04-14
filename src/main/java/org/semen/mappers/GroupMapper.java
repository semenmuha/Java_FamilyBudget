package org.semen.mappers;

import org.semen.dto.request.GroupCreateRequest;
import org.semen.dto.response.GroupResponse;
import org.semen.entities.Group;
import org.semen.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GroupMapper {
    public Group toEntity(GroupCreateRequest request) {
        return new Group().setName(request.getName());
    }
    
    public GroupResponse toResponse(Group group) {
        return new GroupResponse()
            .setId(group.getId())
            .setName(group.getName())
            .setCreatorId(group.getCreator().getId())
            .setDateOfCreation(group.getDateOfCreation())
            .setMemberIds(group.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
    }
}