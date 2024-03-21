package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.AttendanceService;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AttendanceController extends BaseReadWriteController<AttendancePayload, Attendance, Long> {
    @Autowired
    AttendanceService attendanceService;
    @PostMapping("/scan")
    public ResponseEntity<?> attendance(@RequestBody AttendancePayload attendancePayload) throws Exception {
        return attendanceService.scan(attendancePayload);
    }

    @PostMapping("members/{memberId}/events/{eventId}/attendance")
    public ResponseEntity<?> attendanceByMemberByEvent(@PathVariable long memberId,
                                                       @PathVariable long eventId){
        return attendanceService.attendanceByMemberByEvent(memberId, eventId);
    }

    @GetMapping("members/{memberId}/attendance")
    public ResponseEntity<?> attendanceByMemberByAccountType(@PathVariable long memberId){
        return attendanceService.attendanceByMemberByAccountType(memberId);
    }
}
