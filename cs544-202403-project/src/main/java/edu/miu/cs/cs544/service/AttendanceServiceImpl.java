package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.dto.AttendanceListDTO;
import edu.miu.cs.cs544.dto.ErrorResponseDTO;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.repository.EventRepository;
import edu.miu.cs.cs544.repository.MemberRepository;
import edu.miu.cs.cs544.repository.ScannerRepository;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AttendanceServiceImpl extends BaseReadWriteServiceImpl<AttendancePayload, Attendance, Long> implements AttendanceService {
    @Autowired
    ScannerRepository scannerRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    EventRepository eventRepository;

    @Override
    public ResponseEntity<?> scan(AttendancePayload attendancePayload) {
        Long scannerId = attendancePayload.getScanner().getId();
        String barCode = attendancePayload.getMember().getBarCode();
        Optional<Scanner> optionalScanner = scannerRepository.findById(scannerId);
        Optional<Member> optionalMember = memberRepository.findMemberByBarCode(barCode);

        if (optionalScanner.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Scanner not found"), HttpStatus.NOT_FOUND);

        if (optionalMember.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Member not found"), HttpStatus.NOT_FOUND);


        Member member = optionalMember.get();
        Scanner scanner = optionalScanner.get();
        Event event = scanner.getEvent();

        if (event.getSchedule().isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(400, String.format("Event %s Does not have any sessions", event.getName())), HttpStatus.BAD_REQUEST);


        if (!event.getParticipants().contains(member))
            return new ResponseEntity<>(new ErrorResponseDTO(400, "Member is not registered for event " + event.getName()), HttpStatus.BAD_REQUEST);

        AccountType accountType = scanner.getAccountType();
        Optional<Account> account = member.getAccounts()
                .stream()
                .filter((a) -> a.getType().equals(accountType))
                .findFirst();

        if (account.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Member Does not have Account type " + accountType), HttpStatus.NOT_FOUND);

        //withdraw point from this account
        account.get().withdraw(1);

        //Now create Attendance
        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setScanner(scanner);
        attendance.setDateTime(attendancePayload.getDateTime());
        attendanceRepository.save(attendance);

        return new ResponseEntity<>(attendance, HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<?> attendanceByMemberByEvent(long memberId, long eventId) {
        //Getting events
        var optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Event not found "), HttpStatus.NOT_FOUND);

        //Getting members
        var optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Member not found "), HttpStatus.NOT_FOUND);

        var memberName = optionalMember.get().getFirstName();

        var event = optionalEvent.get();
        Collection<Session> sessions = event.getSchedule();

        List<Attendance> attendances = attendanceRepository.findAllByMemberIdAndEventId(memberId, eventId);
        List<AttendanceListDTO> attendanceListDTOS = new ArrayList<>();

        for (Session session : sessions) {

            var sessionDate = session.getDate();
            boolean attended = false;
            for (Attendance attendance : attendances) {
                var attendanceDate = attendance.getDateTime().toLocalDate();
                if (attendanceDate.isEqual(sessionDate)) {
                    attended = true;
                    break;
                }
            }


            attendanceListDTOS.add(new AttendanceListDTO(
                    memberName,
                    sessionDate,
                    attended)
            );
        }

        return new ResponseEntity<>(attendanceListDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> attendanceByMemberByAccountType(long memberId) {
        //Getting members
        var optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty())
            return new ResponseEntity<>(new ErrorResponseDTO(404, "Member not found "), HttpStatus.NOT_FOUND);
        var memberName = optionalMember.get().getFirstName();

        var attendanceList = attendanceRepository.findAllByMemberId(memberId);

        HashMap<AccountType, Event> accountTypeEventHashMap = new HashMap<>();

        for (Attendance attendance : attendanceList) {
            accountTypeEventHashMap.put(attendance.getScanner().getAccountType(),attendance.getScanner().getEvent());
        }

        HashMap<AccountType, List<Attendance>> map2 = new HashMap<>();
        for (Attendance attendance : attendanceList) {
            var accountType = attendance.getScanner().getAccountType();
            map2.computeIfAbsent(accountType, k -> new ArrayList<>());
            map2.get(accountType).add(attendance);
        }


        HashMap<AccountType, List<AttendanceListDTO>> resultMap = new HashMap<>();


        for (Map.Entry<AccountType, Event> entry : accountTypeEventHashMap.entrySet()) {

            var accountType = entry.getKey();
            var value = entry.getValue();
            var sessions = value.getSchedule();

            for (Session session : sessions) {

                var sessionDate = session.getDate();
                boolean attended = false;

                for (Attendance attendance : map2.get(accountType)) {
                    var attendanceDate = attendance.getDateTime().toLocalDate();
                    if (attendanceDate.isEqual(sessionDate)) {
                        attended = true;
                        break;
                    }
                }

                resultMap.computeIfAbsent(accountType, k -> new ArrayList<>());
                resultMap.get(accountType).add(new AttendanceListDTO(
                        memberName,
                        sessionDate,
                        attended)
                );
            }
        }

        return new ResponseEntity<>(resultMap, HttpStatus.OK);

    }
}
