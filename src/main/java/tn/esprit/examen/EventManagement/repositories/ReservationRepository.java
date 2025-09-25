package tn.esprit.examen.EventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByEventId(Long eventId);
    // 🔎 New methods for partial search
    List<Reservation> findByUser_UsernameContainingIgnoreCase(String username);

    List<Reservation> findByEvent_TitleContainingIgnoreCase(String title);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

}
