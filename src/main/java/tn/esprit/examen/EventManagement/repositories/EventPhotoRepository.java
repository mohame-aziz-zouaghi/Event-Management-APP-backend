package tn.esprit.examen.EventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.EventPhoto;

import java.util.List;

public interface EventPhotoRepository extends JpaRepository<EventPhoto, Long> {
    List<EventPhoto> findByEventId(Long eventId);
}