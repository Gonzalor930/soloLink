package soloLink.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import soloLink.demo.dto.TeacherCreateDTO;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.TeacherUserRepository;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherUserRepository teacherRepository;

    public TeacherController(TeacherUserRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping
    public ResponseEntity<TeacherUser> createTeacher(@Valid @RequestBody TeacherCreateDTO dto) {
        TeacherUser teacher = new TeacherUser();
        teacher.setName(dto.name());
        teacher.setEmail(dto.email());
        teacher.setPassword(dto.password());
        teacher.setPublicId(dto.publicId());
        teacher.setPricePerHour(dto.pricePerHour());
        teacher.setDescription(dto.description());
        TeacherUser savedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
    }
}