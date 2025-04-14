package org.semen.repo;

import org.semen.entities.Group;
import org.semen.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    void deleteByCreatorAndId(User creator, Long id);

}
