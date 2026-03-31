package soloLink.demo.services;

import org.springframework.stereotype.Service;
import soloLink.demo.models.Availability;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.AvailabilityRepository;
import soloLink.demo.repositories.TeacherUserRepository;
import soloLink.demo.models.Booking;
import soloLink.demo.repositories.BookingRepository;
import soloLink.demo.dto.BookingCreateDTO;
import soloLink.demo.dto.BookingResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublicTeacherService {

    private final TeacherUserRepository teacherRepository;
    private final AvailabilityRepository availabilityRepository;
    private final BookingRepository bookingRepository;

    public PublicTeacherService(
            TeacherUserRepository teacherRepository,
            AvailabilityRepository availabilityRepository,
            BookingRepository bookingRepository) {
        this.teacherRepository = teacherRepository;
        this.availabilityRepository = availabilityRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<LocalTime> getAvailableSlots(String publicId, LocalDate date) {

        TeacherUser teacher = teacherRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();

        List<Availability> rules = availabilityRepository.findByTeacherIdAndDayOfWeek(teacher.getId(), dayOfWeek);

        List<LocalTime> slots = new ArrayList<>();

        for (Availability rule : rules) {
            LocalTime currentSlot = rule.getStartTime();

            while (!currentSlot.plusHours(1).isAfter(rule.getEndTime())) {
                slots.add(currentSlot);
                currentSlot = currentSlot.plusHours(1);
            }
        }
            LocalDateTime startOfDay = date.atStartOfDay(); // 00:00
            LocalDateTime endOfDay = date.atTime(23, 59, 59); // 23:59:59

            List<Booking> existingBookings = bookingRepository
                    .findByTeacherIdAndStartTimeBetween(teacher.getId(), startOfDay, endOfDay);

            List<LocalTime> bookedHours = existingBookings.stream()
                    .map(booking -> booking.getStartTime().toLocalTime())
                    .toList();

            slots.removeAll(bookedHours);
        return slots;
    }

    public BookingResponseDTO createBooking(String publicId, BookingCreateDTO dto) {

        // Busca al profesor
        TeacherUser teacher = teacherRepository.findByPublicId(publicId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        // 2. Extrae la fecha y hora solicitada
        java.time.LocalDate date = dto.startTime().toLocalDate();
        java.time.LocalTime time = dto.startTime().toLocalTime();

        // pregunta si esta disponible

        java.util.List<java.time.LocalTime> availableSlots = getAvailableSlots(publicId, date);

        if (!availableSlots.contains(time)) {
            throw new RuntimeException("El horario seleccionado ya no está disponible o no es válido.");
        }

        // crea la reserva
        Booking booking = new Booking();
        booking.setTeacher(teacher);
        booking.setStudentName(dto.studentName());
        booking.setStudentEmail(dto.studentEmail());
        booking.setStartTime(dto.startTime());
        booking.setEndTime(dto.startTime().plusHours(1)); // Le suma 1 hora automáticamente
        booking.setStatus("PENDING");

        // guarda la reserva
        Booking savedBooking = bookingRepository.save(booking);

        // devuelve la respuesta
        return new BookingResponseDTO(
                savedBooking.getId(),
                savedBooking.getStudentName(),
                savedBooking.getStartTime(),
                savedBooking.getEndTime(),
                savedBooking.getStatus()
        );
    }
}