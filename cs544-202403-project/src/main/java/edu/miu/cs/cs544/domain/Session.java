package edu.miu.cs.cs544.domain;

import edu.miu.common.domain.AuditData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Session implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "session_date")
    private LocalDate date;

    @Embedded
    AuditData auditData = new AuditData();

    public Session(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
