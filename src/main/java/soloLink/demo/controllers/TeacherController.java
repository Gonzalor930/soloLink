package soloLink.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soloLink.demo.dto.*;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.services.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherUser> createTeacher(@Valid @RequestBody TeacherCreateDTO dto) {
        TeacherUser savedTeacher = teacherService.createTeacher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
    }

    @PostMapping("/{id}/availabilities")
    public ResponseEntity<AvailabilityResponseDTO> addAvailability(
            @PathVariable Long id,
            @Valid @RequestBody AvailabilityCreateDTO dto) {
        AvailabilityResponseDTO response = teacherService.addAvailability(id, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<BookingResponseDTO>> getBookings(@PathVariable Long id) {
        List<BookingResponseDTO> bookings = teacherService.getTeacherBookings(id);

        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/{id}/bookings/{bookingId}/status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(
            @PathVariable Long id,
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingStatusUpdateDTO dto) {
        BookingResponseDTO updatedBooking = teacherService.updateBookingStatus(id, bookingId, dto);

        return ResponseEntity.ok(updatedBooking);
    }

    @PatchMapping("/{id}/profile")
    public ResponseEntity<soloLink.demo.dto.TeacherPublicProfileDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody soloLink.demo.dto.TeacherProfileUpdateDTO dto) {

        soloLink.demo.dto.TeacherPublicProfileDTO updatedProfile = teacherService.updateProfile(id, dto);
        return ResponseEntity.ok(updatedProfile);
    }
}