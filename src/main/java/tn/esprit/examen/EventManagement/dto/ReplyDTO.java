package tn.esprit.examen.EventManagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDTO {
    private Long id;
    private String content;
    private Long commentId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
