package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scanner implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String code;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "evt_id")
    private Event event;
    @Embedded
    AuditData auditData = new AuditData();
}
