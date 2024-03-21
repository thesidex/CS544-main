package edu.miu.cs.cs544.dto;

public record ErrorResponseDTO(
        int statusCode,
        String message
) {
}
