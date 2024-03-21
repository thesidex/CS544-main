package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface AttendanceService  extends BaseReadWriteService<AttendancePayload, Attendance, Long> {
    ResponseEntity<?> scan(AttendancePayload attendancePayload);

    ResponseEntity<?> attendanceByMemberByEvent(@PathVariable long memberId,
                                                @PathVariable long eventId);

    ResponseEntity<?> attendanceByMemberByAccountType(@PathVariable long memberId);
}

