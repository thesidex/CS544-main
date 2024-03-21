package edu.miu.cs.cs544.controller;

import edu.miu.common.controller.BaseReadWriteController;
import edu.miu.cs.cs544.domain.Scanner;
import edu.miu.cs.cs544.service.ScannerService;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scanners")
public class ScannerController extends BaseReadWriteController<ScannerPayload, Scanner, Long> {
    @Autowired
    ScannerService service;
    @GetMapping("/{scannerCode}/records")
    public ResponseEntity<?> getAllRecords(@PathVariable String scannerCode){
        List<AttendancePayload> attendances = service.getAllRecords(scannerCode);
        return new ResponseEntity<>(attendances,HttpStatus.OK);
    }
}
