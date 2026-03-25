package soloLink.demo.services;

import org.springframework.stereotype.Service;
import soloLink.demo.dto.AvailabilityCreateDTO;
import soloLink.demo.dto.AvailabilityResponseDTO;
import soloLink.demo.dto.TeacherCreateDTO;
import soloLink.demo.models.Availability;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.AvailabilityRepository;
import soloLink.demo.repositories.TeacherUserRepository;

@Service
public class TeacherService {
    private final TeacherUserRepository teacherRepository;
    private final AvailabilityRepository availabilityRepository;

    public TeacherService(TeacherUserRepository teacherRepository, AvailabilityRepository availabilityRepository) {
        this.teacherRepository = teacherRepository;
        this.availabilityRepository = availabilityRepository;
    }
    public TeacherUser createTeacher(TeacherCreateDTO dto) {
        TeacherUser teacher = new TeacherUser();
        teacher.setName(dto.name());
        teacher.setEmail(dto.email());
        teacher.setPassword(dto.password());
        teacher.setPublicId(dto.publicId());
        teacher.setPricePerHour(dto.pricePerHour());
        teacher.setDescription(dto.description());
        return teacherRepository.save(teacher);
    }
    public AvailabilityResponseDTO addAvailability(Long teacherId, AvailabilityCreateDTO dto) {
        TeacherUser teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + teacherId));
        Availability availability = new Availability();
        availability.setTeacher(teacher);
        availability.setDayOfWeek(dto.dayOfWeek());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());

        Availability savedAvailability = availabilityRepository.save(availability);

        return new AvailabilityResponseDTO(
                savedAvailability.getId(),
                savedAvailability.getDayOfWeek(),
                savedAvailability.getStartTime(),
                savedAvailability.getEndTime()
        );
    }

}