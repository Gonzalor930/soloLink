package soloLink.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soloLink.demo.models.TeacherUser;

import java.util.Optional;
public interface TeacherUserRepository extends JpaRepository<TeacherUser, Long> {
    //Busqueda por link (publicId)
    Optional<TeacherUser> findByPublicId(String publicId);
    //busqueda por mail
    Optional<TeacherUser> findByEmail(String email);
}