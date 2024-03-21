package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.dto.MemberRolesResponseDTO;
import edu.miu.cs.cs544.dto.ModifyMemberRoleResponseDTO;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.repository.RoleRepository;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.service.exception.MemberNotFoundException;
import edu.miu.cs.cs544.service.exception.RoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberServiceImpl extends BaseReadWriteServiceImpl<MemberPayload, Member, Long> implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;


    @Override
    public MemberRolesResponseDTO getRoles(long memberId) throws MemberNotFoundException {
        final var member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(memberId)
        );
        return new MemberRolesResponseDTO(
                memberId,
                List.copyOf(member.getRole())
        );
    }

    @Transactional
    @Override
    public ModifyMemberRoleResponseDTO assignRole(long roleId, long memberId) throws MemberNotFoundException, RoleNotFoundException {
        final var member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(memberId)
        );
        final var role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        final var memberRoles = member.getRole();
        final var memberAccounts = member.getAccounts();
        final var supportedAccountTypes = memberAccounts.stream().map(Account::getType).collect(Collectors.toSet());
        final var notYetSupportedAccountTypes = new HashSet<>(role.getDefaultAccountTypes());
        notYetSupportedAccountTypes.removeAll(supportedAccountTypes);
        memberRoles.add(role);
        for (final var accountType : notYetSupportedAccountTypes) {
            memberAccounts.add(
                    new Account(
                            "", "", accountType
                    )
            );
        }
        memberRepository.save(member);
        return new ModifyMemberRoleResponseDTO(
                memberId,
                List.copyOf(member.getRole()),
                List.copyOf(memberAccounts)
        );
    }

    @Transactional
    @Override
    public ModifyMemberRoleResponseDTO removeRole(long roleId, long memberId) throws MemberNotFoundException, RoleNotFoundException {
        final var member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(memberId)
        );
        final var role = roleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException(roleId)
        );
        member.getRole().remove(role);
        memberRepository.save(member);
        return new ModifyMemberRoleResponseDTO(
                memberId,
                List.copyOf(member.getRole()),
                List.copyOf(member.getAccounts())
        );
    }
}
