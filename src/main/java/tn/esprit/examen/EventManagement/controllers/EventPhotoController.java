package tn.esprit.examen.EventManagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.examen.EventManagement.entities.EventPhoto;
import tn.esprit.examen.EventManagement.repositories.EventPhotoRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event-photos")
@RequiredArgsConstructor
public class EventPhotoController {

    private final EventPhotoRepository eventPhotoRepository;

    @GetMapping("/{eventId}")
    public ResponseEntity<List<String>> getPhotosByEvent(@PathVariable Long eventId) {
        List<EventPhoto> photos = eventPhotoRepository.findByEventId(eventId);
        // Return just the URLs
        List<String> photoUrls = photos.stream()
                .map(EventPhoto::getUrl)
                .collect(Collectors.toList());
        return ResponseEntity.ok(photoUrls);
    }
}
