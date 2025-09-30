package tn.esprit.examen.EventManagement.services;

import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.entities.Comment;
import tn.esprit.examen.EventManagement.entities.Event;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.repositories.CommentRepository;
import tn.esprit.examen.EventManagement.repositories.IEventRepository;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IEventRepository eventRepository;

    public CommentServiceImpl(CommentRepository commentRepository, IUserRepository userRepository, IEventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Comment addComment(Long userId, Long eventId, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getCommentsByEvent(Long eventId) {
        return commentRepository.findByEventId(eventId);
    }

    @Override
    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByAuthorId(userId);
    }
}

