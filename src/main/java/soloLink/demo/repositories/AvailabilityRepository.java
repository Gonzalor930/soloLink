package soloLink.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soloLink.demo.models.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
