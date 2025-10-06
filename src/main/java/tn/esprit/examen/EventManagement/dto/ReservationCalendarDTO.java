package tn.esprit.examen.EventManagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationCalendarDTO {
    private Long reservationId;
    private Long eventId;
    private String eventTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

}
