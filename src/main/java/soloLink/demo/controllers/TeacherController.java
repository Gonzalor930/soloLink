package soloLink.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soloLink.demo.dto.TeacherCreateDTO;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.services.TeacherService;

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
}