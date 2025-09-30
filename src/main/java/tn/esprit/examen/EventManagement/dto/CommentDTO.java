package tn.esprit.examen.EventManagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long eventId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

