package soloLink.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soloLink.demo.dto.TeacherPublicProfileDTO;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.TeacherUserRepository;
import soloLink.demo.services.PublicTeacherService;
import soloLink.demo.dto.BookingCreateDTO;
import soloLink.demo.dto.BookingResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public") // La ruta base
public class PublicTeacherController {

    private final TeacherUserRepository teacherRepository;
    private final PublicTeacherService publicTeacherService;

    public PublicTeacherController(TeacherUserRepository teacherRepository, PublicTeacherService publicTeacherService) {
        this.teacherRepository = teacherRepository;
        this.publicTeacherService = publicTeacherService;
    }
    @GetMapping("/teachers/{publicId}")
    public ResponseEntity<TeacherPublicProfileDTO> getTeacherProfile(@PathVariable String publicId) {

        Optional<TeacherUser> teacherOpt = teacherRepository.findByPublicId(publicId);

        if (teacherOpt.isPresent()) {
            TeacherUser teacher = teacherOpt.get();
            TeacherPublicProfileDTO dto = new TeacherPublicProfileDTO(
                    teacher.getName(),
                    teacher.getDescription(),
                    teacher.getPricePerHour(),
                    teacher.getPublicId()
            );

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/teachers/{publicId}/slots")
    public ResponseEntity<List<LocalTime>> getTeacherSlots(
            @PathVariable String publicId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime> slots = publicTeacherService.getAvailableSlots(publicId, date);

        return ResponseEntity.ok(slots);
    }

    @PostMapping("/teachers/{publicId}/bookings")
    public ResponseEntity<BookingResponseDTO> createBooking(
            @PathVariable String publicId,
            @Valid @RequestBody BookingCreateDTO dto) {

        BookingResponseDTO response = publicTeacherService.createBooking(publicId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}