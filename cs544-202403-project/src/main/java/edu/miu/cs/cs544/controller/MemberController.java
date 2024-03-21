package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.dto.ErrorResponseDTO;
import edu.miu.cs.cs544.service.MemberService;
import edu.miu.cs.cs544.service.exception.MemberNotFoundException;
import edu.miu.cs.cs544.service.exception.RoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.contract.MemberPayload;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController extends BaseReadWriteController<MemberPayload, Member, Long> {

    private final MemberService memberService;

    @RequestMapping(value = "/{memberId}/roles", method = RequestMethod.GET)
    ResponseEntity<?> getRoles(@PathVariable long memberId){
        try {
            return new ResponseEntity<>(
                    memberService.getRoles(memberId),
                    HttpStatusCode.valueOf(200)
            );
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(
                    new ErrorResponseDTO(
                            404,
                            "Could not find a member with id " + e.requestedMemberId
                    ),
                    HttpStatusCode.valueOf(404)
            );
        }
    }

    @RequestMapping(value = "/{memberId}/roles/{roleId}", method = RequestMethod.POST)
    ResponseEntity<?> assignRole(@PathVariable long memberId, @PathVariable Long roleId) {
        try {
            return new ResponseEntity<>(
                    memberService.assignRole(roleId,memberId),
                    HttpStatusCode.valueOf(200)
            );
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(
              new ErrorResponseDTO(
                      404,
                      "Could not find a member with id " + e.requestedMemberId
              ),
                    HttpStatusCode.valueOf(404)
            );
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(
                    new ErrorResponseDTO(
                            404,
                            "Could not find a role with id " + e.requestedRoleId
                            ),
                    HttpStatusCode.valueOf(404)
            );
        }
    }

    @RequestMapping(value = "/{memberId}/roles/{roleId}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeRole(@PathVariable long memberId, @PathVariable long roleId){
        try {
            return new ResponseEntity<>(
                    memberService.removeRole(roleId, memberId),
                    HttpStatusCode.valueOf(200)
            );
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(
                    new ErrorResponseDTO(
                            404,
                            "Could not find a member with id " + e.requestedMemberId
                    ),
                    HttpStatusCode.valueOf(404)
            );
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>(
                    new ErrorResponseDTO(
                            404,
                            "Could not find a role with id " + e.requestedRoleId
                    ),
                    HttpStatusCode.valueOf(404)
            );
        }
    }
}
