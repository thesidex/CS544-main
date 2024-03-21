package edu.miu.cs.cs544.service.contract;
import edu.miu.common.domain.AuditData;
import edu.miu.cs.cs544.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class RolePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private AuditData auditData;
    private Set<AccountType> defaultAccountTypes;
}
