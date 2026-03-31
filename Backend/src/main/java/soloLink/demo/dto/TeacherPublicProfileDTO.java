package soloLink.demo.dto;

// record inmutable, genera automáticamente constructores y getters.
public record TeacherPublicProfileDTO(
        String name,
        String description,
        Double pricePerHour,
        String publicId
) {}