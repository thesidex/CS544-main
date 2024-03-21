package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteServiceImpl;
import edu.miu.cs.cs544.domain.Attendance;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.repository.AttendanceRepository;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScannerServiceImpl extends BaseReadWriteServiceImpl<ScannerPayload, Scanner, Long> implements ScannerService {
    @Autowired
    AttendanceRepository repository;
    @Override
    public List<AttendancePayload> getAllRecords(String scannerCode) {
        List<Attendance> attendances = repository.findAllRecordsByScannerCode(scannerCode);
        List<AttendancePayload> attendancePayloads = new ArrayList<>();
        for(Attendance attendance : attendances){
            AttendancePayload payload = new AttendancePayload();
            payload.setScanner(attendance.getScanner());
            payload.setMember(attendance.getMember());
            attendancePayloads.add(payload);
        }
        return attendancePayloads;
    }
}
