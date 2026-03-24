package soloLink.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soloLink.demo.dto.TeacherPublicProfileDTO;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.TeacherUserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/public") // La ruta base
public class PublicTeacherController {

    private final TeacherUserRepository teacherRepository;

    public PublicTeacherController(TeacherUserRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Endpoint: GET /public/teachers/{publicId}
    @GetMapping("/teachers/{publicId}")
    public ResponseEntity<TeacherPublicProfileDTO> getTeacherProfile(@PathVariable String publicId) {

        Optional<TeacherUser> teacherOpt = teacherRepository.findByPublicId(publicId);

        if (teacherOpt.isPresent()) {
            TeacherUser teacher = teacherOpt.get();

            // Transformamos la entidad (que tiene password) al DTO (seguro)
            TeacherPublicProfileDTO dto = new TeacherPublicProfileDTO(
                    teacher.getName(),
                    teacher.getDescription(),
                    teacher.getPricePerHour(),
                    teacher.getPublicId()
            );

            return ResponseEntity.ok(dto); // Devuelve 200 OK con el DTO
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si el link no existe
        }
    }
}