package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.Comment;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getAuthor().getId());
        dto.setEventId(comment.getEvent().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }
}

