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
public class Location implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private LocationType type;
    @Embedded
    AuditData auditData = new AuditData();

    public Location(String name, String description, LocationType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
