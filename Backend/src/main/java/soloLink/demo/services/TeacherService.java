package soloLink.demo.services;

import org.springframework.stereotype.Service;
import soloLink.demo.dto.*;
import soloLink.demo.models.Availability;
import soloLink.demo.models.Booking;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.AvailabilityRepository;
import soloLink.demo.repositories.TeacherUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import soloLink.demo.repositories.BookingRepository;

import java.util.List;

@Service
public class TeacherService {
    private final BookingRepository bookingRepository;
    private final TeacherUserRepository teacherRepository;
    private final AvailabilityRepository availabilityRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(BookingRepository bookingRepository, TeacherUserRepository teacherRepository, AvailabilityRepository availabilityRepository, PasswordEncoder passwordEncoder) {
        this.bookingRepository = bookingRepository;
        this.teacherRepository = teacherRepository;
        this.availabilityRepository = availabilityRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public TeacherUser createTeacher(TeacherCreateDTO dto) {
        TeacherUser teacher = new TeacherUser();
        teacher.setName(dto.name());
        teacher.setEmail(dto.email());
        teacher.setPublicId(dto.publicId());
        teacher.setPricePerHour(dto.pricePerHour());
        teacher.setDescription(dto.description());
        String encodedPassword = passwordEncoder.encode(dto.password());
        teacher.setPassword(encodedPassword);
        return teacherRepository.save(teacher);
    }
    public AvailabilityResponseDTO addAvailability(String email, AvailabilityCreateDTO dto) {
        TeacherUser teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        Availability availability = new Availability();
        availability.setTeacher(teacher);
        availability.setDayOfWeek(dto.dayOfWeek());
        availability.setStartTime(dto.startTime());
        availability.setEndTime(dto.endTime());
        Availability savedAvailability = availabilityRepository.save(availability);

        return new AvailabilityResponseDTO(
                savedAvailability.getId(), savedAvailability.getDayOfWeek(),
                savedAvailability.getStartTime(), savedAvailability.getEndTime()
        );
    }

    public List<BookingResponseDTO> getTeacherBookings(String email) {
        TeacherUser teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        List<Booking> bookings = bookingRepository.findByTeacherIdOrderByStartTimeAsc(teacher.getId());

        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getId(), booking.getStudentName(), booking.getStartTime(),
                        booking.getEndTime(), booking.getStatus()
                )).toList();
    }

    public BookingResponseDTO updateBookingStatus(String email, Long bookingId, BookingStatusUpdateDTO dto) {
        TeacherUser teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        if (!booking.getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Esta reserva no te pertenece");
        }
        booking.setStatus(dto.status());
        Booking updatedBooking = bookingRepository.save(booking);

        return new BookingResponseDTO(
                updatedBooking.getId(), updatedBooking.getStudentName(),
                updatedBooking.getStartTime(), updatedBooking.getEndTime(), updatedBooking.getStatus()
        );
    }

    public soloLink.demo.dto.TeacherPublicProfileDTO updateProfile(String email, soloLink.demo.dto.TeacherProfileUpdateDTO dto) {
        TeacherUser teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        if (dto.description() != null) {
            teacher.setDescription(dto.description());
        }
        TeacherUser updatedTeacher = teacherRepository.save(teacher);

        return new soloLink.demo.dto.TeacherPublicProfileDTO(
                updatedTeacher.getName(), updatedTeacher.getDescription(),
                updatedTeacher.getPricePerHour(), updatedTeacher.getPublicId()
        );
    }
}