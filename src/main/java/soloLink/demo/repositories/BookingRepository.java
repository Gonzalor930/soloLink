package soloLink.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soloLink.demo.models.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTeacherIdAndStartTimeBetween(Long teacherId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}