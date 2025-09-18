package tn.esprit.examen.EventManagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private Long id;
    private Long userId;
    private Long eventId;
    private LocalDateTime reservationDate;
    private String status;
    private String ticketNumber;
    private String username;
    private String eventtitle;
}
