package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Role;
import edu.miu.cs.cs544.service.contract.RolePayload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseReadWriteController<RolePayload, Role, Long> {
}
