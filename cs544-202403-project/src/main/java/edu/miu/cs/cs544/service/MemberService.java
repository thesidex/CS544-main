package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.dto.MemberRolesResponseDTO;
import edu.miu.cs.cs544.service.contract.MemberPayload;
import edu.miu.cs.cs544.dto.ModifyMemberRoleResponseDTO;
import edu.miu.cs.cs544.service.exception.MemberNotFoundException;
import edu.miu.cs.cs544.service.exception.RoleNotFoundException;


public interface MemberService extends BaseReadWriteService<MemberPayload, Member, Long> {

    MemberRolesResponseDTO getRoles(long memberId) throws MemberNotFoundException;

    ModifyMemberRoleResponseDTO assignRole(long roleId, long memberId) throws MemberNotFoundException, RoleNotFoundException;

    ModifyMemberRoleResponseDTO removeRole(long roleId, long memberId) throws MemberNotFoundException, RoleNotFoundException;
}
