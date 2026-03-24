package soloLink.demo.services;

import org.springframework.stereotype.Service;
import soloLink.demo.dto.TeacherCreateDTO;
import soloLink.demo.models.TeacherUser;
import soloLink.demo.repositories.TeacherUserRepository;

@Service
public class TeacherService {
    private final TeacherUserRepository teacherRepository;

    public TeacherService(TeacherUserRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    public TeacherUser createTeacher(TeacherCreateDTO dto) {
        TeacherUser teacher = new TeacherUser();
        teacher.setName(dto.name());
        teacher.setEmail(dto.email());
        teacher.setPassword(dto.password());
        teacher.setPublicId(dto.publicId());
        teacher.setPricePerHour(dto.pricePerHour());
        teacher.setDescription(dto.description());
        return teacherRepository.save(teacher);
    }
}