package tn.esprit.examen.EventManagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.dto.EventDTO;
import tn.esprit.examen.EventManagement.entities.Event;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.repositories.IEventRepository;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final IEventRepository eventRepository;
    private final IUserRepository userRepository;

    // Create Event
    public EventDTO createEvent(EventDTO dto) {
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        LocalDateTime start = dto.getStartDate();
        LocalDateTime end = dto.getEndDate();

        // Validate 1-hour minimum difference
        if (Duration.between(start, end).toMinutes() < 60) {
            throw new IllegalArgumentException("End date must be at least 1 hour after start date");
        }



        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        if (dto.getCapacity() < 5 && dto.getCapacity() != 0) {
            throw new IllegalArgumentException("Capacity must be at least 5 members");
        }else {
            event.setCapacity(dto.getCapacity());
        }
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setOrganizer(organizer);

        Event saved = eventRepository.save(event);

        return mapToDTO(saved);
    }

    // Get all events
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get event by ID
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToDTO(event);
    }

    // Update Event
    public EventDTO updateEvent(Long id, EventDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        LocalDateTime start = dto.getStartDate();
        LocalDateTime end = dto.getEndDate();

        // Validate 1-hour minimum difference
        if (Duration.between(start, end).toMinutes() < 60) {
            throw new IllegalArgumentException("End date must be at least 1 hour after start date");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        if (dto.getCapacity() < 5 && dto.getCapacity() != 0) {
            throw new IllegalArgumentException("Capacity must be at least 5 members");
        }else {
            event.setCapacity(dto.getCapacity());
        }
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setOrganizer(event.getOrganizer());

        Event updated = eventRepository.save(event);
        return mapToDTO(updated);
    }

    // Delete Event
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(event);
    }

    // Mapper
    private EventDTO mapToDTO(Event event) {
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
                event.getUpdatedAt()
        );
    }
}
