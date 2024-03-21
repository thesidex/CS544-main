package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.repository.RoleRepository;
import edu.miu.cs.cs544.service.contract.RolePayload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends BaseReadWriteServiceImpl<RolePayload, Role, Long> implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void deleteInBatch(Iterable<Long> ids) {
        roleRepository.deleteAllRoleRefsInMemberRole(ids);
        super.deleteInBatch(ids);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        roleRepository.deleteAllRoleRefsInMemberRole(List.of(id));
        super.delete(id);
    }
}
