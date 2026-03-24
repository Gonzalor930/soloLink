package soloLink.demo.controllers;

import org.springframework.web.bind.annotation.*;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.TeacherUserRepository;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherUserRepository teacherRepository;

    public TeacherController(TeacherUserRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Endpoint de prueba
    @PostMapping
    public TeacherUser createTeacher(@RequestBody TeacherUser teacher) {
        return teacherRepository.save(teacher);
    }

    // Endpoint de prueba
    @GetMapping
    public Iterable<TeacherUser> getAllTeachers() {
        return teacherRepository.findAll();
    }
}