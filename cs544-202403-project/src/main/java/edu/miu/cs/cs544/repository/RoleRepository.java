package edu.miu.cs.cs544.repository;

import edu.miu.common.repository.BaseRepository;
import edu.miu.cs.cs544.domain.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    @Modifying
    @Query(value = "DELETE FROM member_role WHERE role_id in (:roleIds)", nativeQuery = true)
    void deleteAllRoleRefsInMemberRole(Iterable<Long> roleIds);
}
