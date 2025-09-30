package tn.esprit.examen.EventManagement.dto;

import tn.esprit.examen.EventManagement.entities.Reply;

public class ReplyMapper {

    public static ReplyDTO toDTO(Reply reply) {
        ReplyDTO dto = new ReplyDTO();
        dto.setId(reply.getId());
        dto.setContent(reply.getContent());
        dto.setUserId(reply.getAuthor().getId());
        dto.setCommentId(reply.getComment().getId());
        dto.setCreatedAt(reply.getCreatedAt());
        dto.setUpdatedAt(reply.getUpdatedAt());
        return dto;
    }
}

