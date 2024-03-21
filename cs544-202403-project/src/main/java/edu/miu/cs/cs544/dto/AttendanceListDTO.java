package edu.miu.cs.cs544.dto;

import java.time.LocalDate;

public record AttendanceListDTO(
        String name,
        LocalDate date,
        boolean attended
) {

}

