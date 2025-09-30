package tn.esprit.examen.EventManagement.services;

import tn.esprit.examen.EventManagement.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long userId, Long eventId, String content);
    Comment updateComment(Long commentId, String newContent);
    void deleteComment(Long commentId);
    List<Comment> getCommentsByEvent(Long eventId);
    List<Comment> getCommentsByUser(Long userId);
}

