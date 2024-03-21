package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.Role;

import java.util.Collection;

public record ModifyMemberRoleResponseDTO(
        long memberId,
        Collection<Role> roles,
        Collection<Account> accounts
) {
}
