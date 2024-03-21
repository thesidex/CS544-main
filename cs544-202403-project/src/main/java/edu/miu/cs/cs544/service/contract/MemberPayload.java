package edu.miu.cs.cs544.service.contract;

import edu.miu.common.domain.AuditData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MemberPayload implements Serializable {

    public static final long serialVersionUID = 1L;

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String barCode;

    private AuditData auditData;

}
