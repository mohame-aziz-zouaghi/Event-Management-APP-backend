package tn.esprit.examen.EventManagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.EventManagement.dto.ReservationDTO;
import tn.esprit.examen.EventManagement.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> create(@RequestParam Long userId, @RequestParam Long eventId) {
        return ResponseEntity.ok(reservationService.createReservation(userId, eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ReservationDTO>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(reservationService.getReservationsByEvent(eventId));
    }

    // Search by username (partial match)
    @GetMapping("/user/username/{username}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(reservationService.getReservationsByUsername(username));
    }

    // Search by event title (partial match)
    @GetMapping("/event/title/{title}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByEventTitle(@PathVariable String title) {
        return ResponseEntity.ok(reservationService.getReservationsByEventTitle(title));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReservationDTO>> getAll() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok("Reservation cancelled");
    }
}
