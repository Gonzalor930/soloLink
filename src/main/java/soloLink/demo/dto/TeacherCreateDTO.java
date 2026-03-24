package soloLink.demo.dto;

//Para manejo de errores
import jakarta.validation.constraints.*;

//Para manejo de nuevos profesores
public record TeacherCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El identificador público es obligatorio")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "El publicId solo puede contener minúsculas, números y guiones")
        String publicId,

        @NotNull(message = "El precio por hora es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        Double pricePerHour,

        String description
) {}