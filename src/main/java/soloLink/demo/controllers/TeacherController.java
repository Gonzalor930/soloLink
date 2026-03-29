package soloLink.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soloLink.demo.dto.*;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.services.TeacherService;

import java.security.Principal;
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

    @PostMapping("/me/availabilities")
    public ResponseEntity<AvailabilityResponseDTO> addAvailability(
            Principal principal,
            @Valid @RequestBody AvailabilityCreateDTO dto) {
                AvailabilityResponseDTO response = teacherService.
                        addAvailability(principal.getName(), dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me/bookings")
    public ResponseEntity<List<BookingResponseDTO>> getBookings(Principal principal) {
                List<BookingResponseDTO> bookings = teacherService.
                        getTeacherBookings(principal.getName());
                return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/me/bookings/{bookingId}/status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(
            Principal principal,
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingStatusUpdateDTO dto) {
                BookingResponseDTO updatedBooking = teacherService.
                        updateBookingStatus(principal.getName(), bookingId, dto);
                return ResponseEntity.ok(updatedBooking);
    }

    @PatchMapping("/me/profile")
    public ResponseEntity<soloLink.demo.dto.TeacherPublicProfileDTO> updateProfile(
            Principal principal,
            @RequestBody soloLink.demo.dto.TeacherProfileUpdateDTO dto) {
                soloLink.demo.dto.TeacherPublicProfileDTO updatedProfile = teacherService.
                        updateProfile(principal.getName(), dto);
                return ResponseEntity.ok(updatedProfile);
    }
}