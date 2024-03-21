package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.domain.Session;
import edu.miu.cs.cs544.service.EventService;
import edu.miu.cs.cs544.service.SessionService;
import edu.miu.cs.cs544.service.contract.EventPayload;

import edu.miu.cs.cs544.service.contract.SessionPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController extends BaseReadWriteController<EventPayload, Event, Long> {


    @Autowired
    SessionService sessionService;

    @PostMapping("{eventId}/sessions")
    public ResponseEntity<?> createSessionByEventId(@PathVariable long eventId, @RequestBody Session session) {
        return sessionService.createSessionByEventId(session, eventId);
    }

    @GetMapping("{eventId}/sessions")
    public ResponseEntity<?> findSessionsByEventId(@PathVariable long eventId) {
        return sessionService.findSessionsByEventId(eventId);
    }

    @PutMapping("{eventId}/sessions/{sessionId}")
    public ResponseEntity<?> UpdateSession(@PathVariable long eventId,
                                           @PathVariable long sessionId,
                                           @RequestBody SessionPayload sessionPayload) {
        return sessionService.updateSessionByEventIdAndSessionId(eventId, sessionId, sessionPayload);
    }

    @DeleteMapping("{eventId}/sessions/{sessionId}")
    public ResponseEntity<?> deleteSessionById(@PathVariable long eventId,
                                               @PathVariable long sessionId) {
        return sessionService.deleteSessionByEventIdAndSessionId(eventId, sessionId);
    }

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}/attendance")
    public ResponseEntity<Map<Member, List<Attendance>>> calculateAttendance(@PathVariable Long eventId) {
        Map<Member, List<Attendance>> attendanceMap = eventService.calculateAttendance(eventId);
        return ResponseEntity.ok(attendanceMap);
    }

    @PostMapping("/{eventId}/members/{memberId}/register-for-event")
    public ResponseEntity<?> registerForEvent(@PathVariable long eventId,
                                              @PathVariable long memberId
    )
    {
        return eventService.registerMember(eventId,memberId);
    }
}


