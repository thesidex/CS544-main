package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Member;
import edu.miu.cs.cs544.dto.ErrorResponseDTO;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.service.contract.EventPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventServiceImpl extends BaseReadWriteServiceImpl<EventPayload, Event, Long> implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    public EventServiceImpl(EventRepository eventRepository, AttendanceRepository attendanceRepository) {
        this.eventRepository = eventRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Map<Member, List<Attendance>> calculateAttendance(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event == null) {
            return new HashMap<>();
        }


        Map<Member, List<Attendance>> attendanceMap = new HashMap<>();

        for (Member member : event.getParticipants()) {
            List<Attendance> memberAttendance = attendanceRepository.findAllByMemberId(member.getId());
            attendanceMap.put(member, memberAttendance);
        }

        return attendanceMap;
    }

    @Override
    public ResponseEntity<?> registerMember(long eventId, long memberId) {
        var optionalEvent = eventRepository.findById(eventId);
        var optionalMember = memberRepository.findById(memberId);
        if (optionalEvent.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Event not found"), HttpStatus.NOT_FOUND);
        if (optionalMember.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Member not found"), HttpStatus.NOT_FOUND);

        var event = optionalEvent.get();
        //Adding the member to events
        event.addParticipant(optionalMember.get());
        //Finally persisting
        eventRepository.save(event);

        return new ResponseEntity<>(event, HttpStatus.NOT_FOUND);

    }

}



