package soloLink.demo.dto;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityCreateDTO(
        @NotNull(message = "El día de la semana es obligatorio (ej: MONDAY)")
        DayOfWeek dayOfWeek,

        @NotNull(message = "La hora de inicio es obligatoria")
        LocalTime startTime,

        @NotNull(message = "La hora de fin es obligatoria")
        LocalTime endTime
) {}