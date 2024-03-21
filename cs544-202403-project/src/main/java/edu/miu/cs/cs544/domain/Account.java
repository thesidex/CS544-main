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
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name = "dValue", nullable = false)
    private double defaultValue = 10000;
    @Column(name = "cValue", nullable = false)
    private double currentValue;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Embedded
    AuditData auditData = new AuditData();

    public Account(String name, String description, AccountType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Account(String name, String description, AccountType type, Double defaultValue, Double currentValue) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.defaultValue = defaultValue;
        this.currentValue = currentValue;
    }

    public void deposit(double amount) {
        currentValue += amount;
    }

    public void withdraw(double amount) {
        currentValue -= amount;
    }
}
