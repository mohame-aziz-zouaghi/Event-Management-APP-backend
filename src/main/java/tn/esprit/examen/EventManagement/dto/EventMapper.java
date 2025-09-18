package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    // 🔹 Full EventDTO mapping
    public static EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getCapacity(),
                event.getStartDate(),
                event.getEndDate(),
                event.getOrganizer() != null ? event.getOrganizer().getId() : null,
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getReservations() != null
                        ? event.getReservations().stream()
                        .map(ReservationMapper::toDTO)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }

    // 🔹 Summary mapping (for UserDTO, etc.)
    public static EventSummaryDTO toSummaryDTO(Event event) {

        return new EventSummaryDTO(
                event.getId(),
                event.getTitle(),
                event.getLocation(),
                event.getCapacity(),
                event.getStartDate(),
                event.getEndDate()
        );
    }

    public static List<EventDTO> toDTOList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<EventSummaryDTO> toSummaryDTOList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }
}
