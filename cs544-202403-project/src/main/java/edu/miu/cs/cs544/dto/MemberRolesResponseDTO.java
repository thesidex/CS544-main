package edu.miu.cs.cs544.dto;

import edu.miu.cs.cs544.domain.Role;

import java.util.Collection;

public record MemberRolesResponseDTO(
        long memberId,
        Collection<Role> roles
) {
}
