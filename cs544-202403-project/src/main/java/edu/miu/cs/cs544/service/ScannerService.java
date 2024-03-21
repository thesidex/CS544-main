package edu.miu.cs.cs544.service;

import edu.miu.common.service.BaseReadWriteService;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;

import java.util.List;

public interface ScannerService extends BaseReadWriteService<ScannerPayload, Scanner, Long> {
    List<AttendancePayload> getAllRecords(String scannerCode);
}
