package tn.esprit.examen.EventManagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.dto.ReservationDTO;
import tn.esprit.examen.EventManagement.dto.ReservationMapper;
import tn.esprit.examen.EventManagement.entities.*;
import tn.esprit.examen.EventManagement.repositories.IEventRepository;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;
import tn.esprit.examen.EventManagement.repositories.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final IUserRepository userRepository;
    private final IEventRepository eventRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              IUserRepository userRepository,
                              IEventRepository eventRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public ReservationDTO createReservation(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check if the user already reserved this event
        boolean alreadyReserved = reservationRepository.existsByUserIdAndEventId(userId, eventId);
        if (alreadyReserved) {
            throw new RuntimeException("You have already reserved a spot for this event");
        }

        // Check capacity
        long reservedCount = reservationRepository.findByEventId(eventId).size();
        if (reservedCount >= event.getCapacity()) {
            throw new RuntimeException("Event capacity full");
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .event(event)
                .reservationDate(LocalDateTime.now())
                .status(ReservationStatus.PENDING)
                .ticketNumber("TICKET-" + System.currentTimeMillis())
                .username(user.getUsername())
                .eventTitle(event.getTitle())
                .build();

        return ReservationMapper.toDTO(reservationRepository.save(reservation));
    }


    public List<ReservationDTO> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByEvent(Long eventId) {
        return reservationRepository.findByEventId(eventId).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Search reservations by username (partial match)
    public List<ReservationDTO> getReservationsByUsername(String username) {
        return reservationRepository.findByUser_UsernameContainingIgnoreCase(username).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Search reservations by event title (partial match)
    public List<ReservationDTO> getReservationsByEventTitle(String title) {
        return reservationRepository.findByEvent_TitleContainingIgnoreCase(title).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
