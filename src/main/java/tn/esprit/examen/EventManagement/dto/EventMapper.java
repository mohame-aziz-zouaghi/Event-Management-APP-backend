package tn.esprit.examen.EventManagement.mappers;

import tn.esprit.examen.EventManagement.dto.EventSummaryDTO;
import tn.esprit.examen.EventManagement.entities.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventSummaryDTO toSummaryDTO(Event event) {
        return new EventSummaryDTO(
                event.getId(),
                event.getTitle(),
                event.getLocation(),
                event.getStartDate(),
                event.getEndDate()
        );
    }

    public static List<EventSummaryDTO> toSummaryDTOList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }
}
