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
public class EventSummaryDTO {
    private Long id;
    private String title;
    private String location;
    private int capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
