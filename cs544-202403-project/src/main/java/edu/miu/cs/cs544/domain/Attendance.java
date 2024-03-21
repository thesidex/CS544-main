package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "scanner_id")
    private Scanner scanner;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Embedded
    AuditData auditData = new AuditData();

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Attendance(Member member, Scanner scanner, LocalDateTime dateTime) {
        this.member = member;
        this.scanner = scanner;
        this.dateTime = dateTime;
    }
}
