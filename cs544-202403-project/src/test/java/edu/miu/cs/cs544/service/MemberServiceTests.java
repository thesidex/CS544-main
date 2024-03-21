package edu.miu.cs.cs544.service;

import edu.miu.common.domain.AuditData;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.repository.RoleRepository;
import lombok.SneakyThrows;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTests {

    private Member createTestMember(){
        return new Member(
                1L,
                "Bruce",
                "Banner",
                "the.hulk@miu.com",
                String.valueOf(1L),
                new HashSet<>(),
                new ArrayList<>(),
                new AuditData()
        );
    }

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    private MemberService memberService;


    @BeforeEach
    public void setUp() {
        memberService = new MemberServiceImpl(
                memberRepository,
                roleRepository
        );
    }

    @SneakyThrows
    @Test
    public void whenRoleRemovedFromMember_ThenEraseFromMemberRoles(){
        final var member = createTestMember();
        Mockito.when(memberRepository.findById(member.getId())).thenReturn(
                Optional.of(member)
        );
        final var role1 = new Role(
                1L,
                "admin",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS))
        );
        final var role2 = new Role(
                2L,
                "admin1",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS, AccountType.VIRTUAL_DOLLAR))
        );
        Mockito.when(roleRepository.findById(role1.getId())).thenReturn(
                Optional.of(role1)
        );
        Mockito.when(roleRepository.findById(role2.getId())).thenReturn(
                Optional.of(role2)
        );
        memberService.assignRole(1L, 1L);

        final var rolesLenBeforeRemoval = memberService.assignRole(2L, 1L).roles().size();

        final var lastRemainingRoles = new ArrayList<>(memberService.removeRole(1L, 1L).roles());

        assert rolesLenBeforeRemoval == 2
                && lastRemainingRoles.size() == 1
                && lastRemainingRoles.getFirst().getName().equals("admin1");
    }


    @SneakyThrows
    @Test
    public void whenRoleAssignedToMember_ThenFindRolesInMember(){
        final var member = createTestMember();
        Mockito.when(memberRepository.findById(member.getId())).thenReturn(
                Optional.of(member)
        );
        final var role1 = new Role(
                1L,
                "admin",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS))
        );
        final var role2 = new Role(
                2L,
                "admin1",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS, AccountType.VIRTUAL_DOLLAR))
        );
        Mockito.when(roleRepository.findById(role1.getId())).thenReturn(
                Optional.of(role1)
        );
        Mockito.when(roleRepository.findById(role2.getId())).thenReturn(
                Optional.of(role2)
        );
        memberService.assignRole(1L, 1L);
        final var latestResponse = memberService.assignRole(2L, 1L);
        final String[] existingRoles = {role1.getName(),role2.getName()};

        Arrays.sort(existingRoles);

        final String[] assignedRoles = latestResponse.roles().stream().map(Role::getName)
                .sorted()
                .toList().toArray(
                new String[latestResponse.roles().size()]
        );

        Assertions.assertArrayEquals(existingRoles,assignedRoles);
    }


    @SneakyThrows
    @Test
    public void whenRoleAssignedToMember_ThenCreateDefaultAccounts() {
        final var member = createTestMember();
        Mockito.when(memberRepository.findById(member.getId())).thenReturn(
                Optional.of(member)
        );
        final var role = new Role(
                1L,
                "admin",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS))
        );
        Mockito.when(roleRepository.findById(role.getId())).thenReturn(
                Optional.of(role)
        );
        final var response = memberService.assignRole(1L,1L);

        final var accountTypes = response
                .accounts()
                .stream()
                .map(Account::getType)
                .sorted()
                .toArray() ;

        final var roleDefaultAccountTypes = role
                .getDefaultAccountTypes()
                .stream().sorted()
                .toArray();

        Assertions.assertArrayEquals(accountTypes, roleDefaultAccountTypes);
    }

    @SneakyThrows
    @Test
    public void whenAssigningRoleMemberAlreadyHasAccountOfAccountType_ThenDontCreateNewOne() {
        final var member = createTestMember();
        Mockito.when(memberRepository.findById(member.getId())).thenReturn(
                Optional.of(member)
        );
        final var role1 = new Role(
                1L,
                "admin",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS))
        );
        final var role2 = new Role(
                2L,
                "admin",
                new AuditData(),
                Sets.newHashSet(List.of(AccountType.ATTENDANCE_POINTS, AccountType.DINING_POINTS, AccountType.VIRTUAL_DOLLAR))
        );
        Mockito.when(roleRepository.findById(role1.getId())).thenReturn(
                Optional.of(role1)
        );
        Mockito.when(roleRepository.findById(role2.getId())).thenReturn(
                Optional.of(role2)
        );

        var response = memberService.assignRole(1L,1L);
        final var accountsAfterRole1 = new ArrayList<>(List.copyOf(response.accounts()));

        response = memberService.assignRole(2L, 1L);
        final var accountsAfterRole2 = new ArrayList<>(List.copyOf(response.accounts()));

        final var afterRole1Size = accountsAfterRole1.size();
        final var afterRole2Size = accountsAfterRole2.size();

        accountsAfterRole2.removeAll(accountsAfterRole1);

        final var lastAddedAccount = accountsAfterRole2.getFirst();

        assert  afterRole1Size == 2
                && afterRole2Size == 3
                && lastAddedAccount.getType() == AccountType.VIRTUAL_DOLLAR;
    }
}
