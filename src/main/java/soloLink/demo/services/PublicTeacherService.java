package soloLink.demo.services;

import org.springframework.stereotype.Service;
import soloLink.demo.models.Availability;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.AvailabilityRepository;
import soloLink.demo.repositories.TeacherUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicTeacherService {

    private final TeacherUserRepository teacherRepository;
    private final AvailabilityRepository availabilityRepository;

    public PublicTeacherService(TeacherUserRepository teacherRepository, AvailabilityRepository availabilityRepository) {
        this.teacherRepository = teacherRepository;
        this.availabilityRepository = availabilityRepository;
    }

    //metodo para slots de tiempo disponibles
    public List<LocalTime> getAvailableSlots(String publicId, LocalDate date) {

        //Busca al profesor por su link público
        TeacherUser teacher = teacherRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        //Obtiene qué día de la semana es la fecha solicitada (ej: Lunes)
        java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();

        //Busca la disponibilidad del profesor para ese día
        List<Availability> rules = availabilityRepository.findByTeacherIdAndDayOfWeek(teacher.getId(), dayOfWeek);

        //Genera los bloques de 1 hora
        List<LocalTime> slots = new ArrayList<>();

        for (Availability rule : rules) {
            LocalTime currentSlot = rule.getStartTime();

            // Mientras el bloque actual + 1 hora no supere la hora de fin del turno
            while (!currentSlot.plusHours(1).isAfter(rule.getEndTime())) {
                slots.add(currentSlot);
                currentSlot = currentSlot.plusHours(1); // Suma 1 hora para el siguiente bloque
            }
        }

        return slots;
    }
}