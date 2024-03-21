package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Email
    private String email;
    @Column(name = "BAR_CODE", nullable = false, unique = true)
    @Lob
    private String barCode;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_role", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> role = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private List<Account> accounts = new ArrayList<>();
    @Embedded
    AuditData auditData = new AuditData();

    public Member(String firstName, String lastName, String email, String barCode, Set<Role> role,
            List<Account> accounts, AuditData auditData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.barCode = barCode;
        this.role = role;
        this.accounts = accounts;
        this.auditData = auditData;
    }
}
