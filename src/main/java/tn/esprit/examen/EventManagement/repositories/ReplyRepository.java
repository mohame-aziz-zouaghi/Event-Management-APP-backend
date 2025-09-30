package tn.esprit.examen.EventManagement.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.EventManagement.entities.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);
    List<Reply> findByAuthorId(Long authorId);
}

