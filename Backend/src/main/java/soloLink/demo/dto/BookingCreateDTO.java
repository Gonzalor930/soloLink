package soloLink.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BookingCreateDTO(
        @NotBlank(message = "El nombre del alumno es obligatorio")
        String studentName,

        @NotBlank(message = "El email del alumno es obligatorio")
        @Email(message = "Formato de email inválido")
        String studentEmail,

        @NotNull(message = "La fecha y hora de inicio es obligatoria")
        LocalDateTime startTime
) {}