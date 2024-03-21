package edu.miu.cs.cs544.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import edu.miu.common.domain.AuditData;
import edu.miu.cs.cs544.domain.Account;
import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.dto.ModifyMemberRoleResponseDTO;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.exception.MemberNotFoundException;
import edu.miu.cs.cs544.service.exception.RoleNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;


@WebMvcTest(MemberController.class)
public class MemberControllerTests {

    @Autowired
    private MockMvc mock;

    @MockBean
    private MemberService memberService;


    @SneakyThrows
    @Test
    public void testRemoveRole(){
        mock.perform(
                MockMvcRequestBuilders.delete(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(200)
        );
        Mockito.verify(memberService, Mockito.times(1)).removeRole(1L, 1L);
    }

    @SneakyThrows
    @Test
    public void testAssignRole(){
        Mockito.when(memberService.assignRole(1L, 1L)).thenReturn(
                new ModifyMemberRoleResponseDTO(
                        1L,
                        List.of(
                                new Role(
                                        1L,
                                        "admin",
                                        new AuditData(),
                                        Set.of(
                                                AccountType.DINING_POINTS
                                        )
                                ),
                                new Role(
                                        1L,
                                        "user",
                                        new AuditData(),
                                        Set.of(
                                                AccountType.DINING_POINTS
                                        )
                                )
                        ),
                        List.of(
                                new Account(
                                        "name",
                                        "descroption",
                                        AccountType.DINING_POINTS
                                )
                        )
                )
        );

        mock.perform(
                MockMvcRequestBuilders.post(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(200)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roles[0].name").value("admin")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roles[1].name").value("user")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.accounts.length()").value(1)
        );
    }

    @SneakyThrows
    @Test
    public void whenRemovingRoleAndNoRoleFound_GivesErrorResponse() {
        Mockito.when(memberService.removeRole(1L, 1L)).thenThrow(
                new RoleNotFoundException(1L)
        );

        mock.perform(
                MockMvcRequestBuilders.delete(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(404)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Could not find a role with id " + 1L)
        );
    }


    @SneakyThrows
    @Test
    public void whenRemovingRoleAndNoMemberFound_GivesErrorResponse() {
        Mockito.when(memberService.removeRole(1L, 1L)).thenThrow(
                new MemberNotFoundException(1L)
        );

        mock.perform(
                MockMvcRequestBuilders.delete(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(404)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Could not find a member with id " + 1L)
        );
    }


    @SneakyThrows
    @Test
    public void whenAssigningRoleAndNoMemberFound_GivesErrorResponse() {
        Mockito.when(memberService.assignRole(1L, 1L)).thenThrow(
                new MemberNotFoundException(1L)
        );

        mock.perform(
                MockMvcRequestBuilders.post(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(404)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Could not find a member with id " + 1L)
        );
    }

    @SneakyThrows
    @Test
    public void whenAssigningRoleAndNoRoleFound_GivesErrorResponse() {
        Mockito.when(memberService.assignRole(1L, 1L)).thenThrow(
                new RoleNotFoundException(1L)
        );

        mock.perform(
                MockMvcRequestBuilders.post(
                                "/members/1/roles/1")
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "123"))
        ).andExpect(
                MockMvcResultMatchers.status().is(404)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.message").value("Could not find a role with id " + 1L)
        );
    }
}
