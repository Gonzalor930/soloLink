package soloLink.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soloLink.demo.models.Availability;

import java.time.DayOfWeek;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByTeacherIdAndDayOfWeek(Long teacherId, DayOfWeek dayOfWeek);
}
