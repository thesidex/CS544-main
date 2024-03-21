package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "START_DATE_TIME", nullable = false)
    private LocalDateTime startDateTime;
    @Column(name = "END_DATE_TIME", nullable = false)
    private LocalDateTime endDateTime;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "evt_id")
    private Collection<Session> schedule = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "registration", joinColumns = { @JoinColumn(name = "evt_id") }, inverseJoinColumns = {
            @JoinColumn(name = "member_id") })
    private List<Member> participants = new ArrayList<>();
    @Embedded
    AuditData auditData = new AuditData();

    public Event(String name, String description, AccountType accountType, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.name = name;
        this.description = description;
        this.accountType = accountType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void addSchedule(Session session) {
        schedule.add(session);
    }

    public void addParticipant(Member member) {
        participants.add(member);
    }
}
