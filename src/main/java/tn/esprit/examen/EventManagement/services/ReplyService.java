package tn.esprit.examen.EventManagement.services;


import tn.esprit.examen.EventManagement.entities.Reply;

import java.util.List;

public interface ReplyService {
    Reply addReply(Long userId, Long commentId, String content);
    Reply updateReply(Long replyId, String newContent);
    void deleteReply(Long replyId);
    List<Reply> getRepliesByComment(Long commentId);
    List<Reply> getRepliesByUser(Long userId);
}

