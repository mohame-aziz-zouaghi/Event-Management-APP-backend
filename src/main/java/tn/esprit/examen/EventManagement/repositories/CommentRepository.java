package tn.esprit.examen.EventManagement.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventId(Long eventId);
    List<Comment> findByAuthorId(Long authorId);
}

