package tn.esprit.examen.EventManagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.examen.EventManagement.dto.EventDTO;
import tn.esprit.examen.EventManagement.dto.ReservationMapper;
import tn.esprit.examen.EventManagement.entities.Event;
import tn.esprit.examen.EventManagement.entities.EventPhoto;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.repositories.EventPhotoRepository;
import tn.esprit.examen.EventManagement.repositories.IEventRepository;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final IEventRepository eventRepository;
    private final IUserRepository userRepository;
    private final EventPhotoRepository eventPhotoRepository;


    // Create Event
    public EventDTO createEvent(EventDTO dto, MultipartFile[] photos) throws IOException {
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

        // 🔹 Handle photos
        if (dto.getPhotoUrls() != null && dto.getPhotoUrls().size() > 5) {
            throw new IllegalArgumentException("You can upload a maximum of 5 photos per event");
        }



        Event savedEvent = eventRepository.save(event);




        // Handle photo uploads
        if (photos != null && photos.length > 0) {
            Path uploadDir = Paths.get("src/main/resources/static/events");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Limit to 5 photos
            int limit = Math.min(photos.length, 5);
            for (int i = 0; i < limit; i++) {
                MultipartFile photo = photos[i];
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path filePath = uploadDir.resolve(fileName);

                // Save file to static folder
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save reference in DB
                EventPhoto eventPhoto = new EventPhoto();
                eventPhoto.setEvent(savedEvent);
                eventPhoto.setUrl("/events/" + fileName);
                eventPhoto.setUploadedAt(LocalDateTime.now());
                eventPhotoRepository.save(eventPhoto);
            }
        }

        return mapToDTO(savedEvent);
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

        // 🔹 Update photos
        if (dto.getPhotoUrls() != null) {
            if (dto.getPhotoUrls().size() > 5) {
                throw new IllegalArgumentException("You can upload a maximum of 5 photos per event");
            }

            // Clear old photos
            event.getPhotos().clear();

            // Add new ones
            List<EventPhoto> newPhotos = dto.getPhotoUrls().stream()
                    .map(url -> EventPhoto.builder()
                            .url(url)
                            .event(event)
                            .build())
                    .collect(Collectors.toList());

            event.getPhotos().addAll(newPhotos);
        }

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
                event.getUpdatedAt(),
                event.getReservations() != null
                        ? event.getReservations().stream()
                        .map(ReservationMapper::toDTO)
                        .collect(Collectors.toList())
                        : List.of(),
                event.getPhotos() != null
                        ? event.getPhotos().stream().map(EventPhoto::getUrl).collect(Collectors.toList())
                        : List.of()
        );
    }
}
