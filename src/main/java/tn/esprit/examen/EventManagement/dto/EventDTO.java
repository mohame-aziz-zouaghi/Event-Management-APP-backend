package tn.esprit.examen.EventManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.examen.EventManagement.entities.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private int capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long organizerId;
    private LocalDateTime CreateAt;// Link to User who organizes
    private LocalDateTime updatedAt;// Link to User who organizes
    private List<ReservationDTO> reservations;
    private List<String> photoUrls;

}
