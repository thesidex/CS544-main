package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Session;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private AccountType accountType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<Session> schedule;
    private List<Member> participants;

    public EventPayload(String name, String description, AccountType accountType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.description = description;
        this.accountType = accountType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

}
