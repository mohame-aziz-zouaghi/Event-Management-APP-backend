package tn.esprit.examen.EventManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.examen.EventManagement.dto.EventDTO;
import tn.esprit.examen.EventManagement.services.EventService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final ObjectMapper objectMapper; // inject configured mapper

    @PostMapping("/add")
    public ResponseEntity<EventDTO> createEvent(
            @RequestPart("event") String eventJson,
            @RequestPart(value = "photos", required = false) MultipartFile[] photos) throws IOException {

        // Use injected mapper (with JavaTimeModule & WRITE_DATES_AS_TIMESTAMPS=false)
        EventDTO dto = objectMapper.readValue(eventJson, EventDTO.class);

        // Log DTO to confirm parsing
        System.out.println("Parsed EventDTO: " + dto);

        EventDTO createdEvent = eventService.createEvent(dto, photos);
        return ResponseEntity.ok(createdEvent);
    }

    // ✅ Get All Events
    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // ✅ Get Event by ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    // ✅ Update Event
    @PutMapping(value = "/update/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long id,
            @RequestPart("event") EventDTO dto,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) throws IOException {

        return ResponseEntity.ok(eventService.UpdateEventphoto(id, dto, photos));
    }

    // ✅ Delete Event
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
}
