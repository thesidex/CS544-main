package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Scanner;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AttendancePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Member member;
    private Scanner scanner;
    private LocalDateTime dateTime;


    public AttendancePayload(Member member, Scanner scanner, LocalDateTime dateTime) {
        this.member = member;
        this.scanner = scanner;
        this.dateTime = dateTime;
    }
}
