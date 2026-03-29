package soloLink.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BookingStatusUpdateDTO(
        @NotBlank(message = "El estado no puede estar vacío")
        @Pattern(regexp = "^(CONFIRMED|CANCELLED)$", message = "El estado solo puede ser CONFIRMED o CANCELLED")
        String status
) {}