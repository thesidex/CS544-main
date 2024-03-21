package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    private AuditData auditData = new AuditData();

    @ElementCollection(targetClass = AccountType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role_default_account_types", joinColumns = @JoinColumn(name = "role_id"))
    private Set<AccountType> defaultAccountTypes = new HashSet<>();
}
