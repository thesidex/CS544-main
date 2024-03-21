package edu.miu.cs.cs544.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventControllerTest {

    private EventService eventService;
    private EventController eventController;

    @BeforeEach
    void setUp() {
        eventService = mock(EventService.class);
        eventController = new EventController(eventService);
    }

    @Test
    void calculateAttendance_ReturnsAttendanceMap() {
        Long eventId = 1L;
        Map<Member, List<Attendance>> expectedAttendanceMap = Collections.singletonMap(
                new Member(), Collections.singletonList(new Attendance())
        );
        when(eventService.calculateAttendance(eventId)).thenReturn(expectedAttendanceMap);

        ResponseEntity<Map<Member, List<Attendance>>> responseEntity = eventController.calculateAttendance(eventId);
        // Assert
        assertSame(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(expectedAttendanceMap, responseEntity.getBody());
    }
}
