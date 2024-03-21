package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private long Id;
    private Date date;
    private LocalTime endTime;
    private Event event;
    private LocalTime startTime;


}
