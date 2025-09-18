package tn.esprit.examen.EventManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.Event;

public interface IEventRepository extends JpaRepository<Event, Long> {
}
