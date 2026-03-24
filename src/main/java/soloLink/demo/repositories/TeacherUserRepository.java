package soloLink.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soloLink.demo.models.TeacherUser;

import java.util.Optional;
// El <TeacherUser, Long> indica la entidad que maneja y el tipo de dato de su ID (@Id)
public interface TeacherUserRepository extends JpaRepository<TeacherUser, Long> {
    //Busqueda por link (publicId)
    Optional<TeacherUser> findByPublicId(String publicId);
}