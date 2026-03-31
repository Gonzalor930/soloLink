package soloLink.demo.dto;

import java.time.LocalDateTime;

public record BookingResponseDTO(
        Long id,
        String studentName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status
) {}