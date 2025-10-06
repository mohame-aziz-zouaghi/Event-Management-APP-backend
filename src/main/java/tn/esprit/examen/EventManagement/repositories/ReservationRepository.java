package tn.esprit.examen.EventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.examen.EventManagement.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByEventId(Long eventId);
    // 🔎 New methods for partial search
    List<Reservation> findByUser_UsernameContainingIgnoreCase(String username);

    List<Reservation> findByEvent_TitleContainingIgnoreCase(String title);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    // Count all reservations by user for an event


    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.user.id = :userId AND r.event.id = :eventId")
    long countByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);

    // Count only active reservations (PENDING or CONFIRMED)
    @Query("SELECT COUNT(r) FROM Reservation r " +
            "WHERE r.user.id = :userId AND r.event.id = :eventId AND r.status IN :statuses")
    long countActiveReservations(@Param("userId") Long userId,
                                 @Param("eventId") Long eventId,
                                 @Param("statuses") List<String> statuses);
}
