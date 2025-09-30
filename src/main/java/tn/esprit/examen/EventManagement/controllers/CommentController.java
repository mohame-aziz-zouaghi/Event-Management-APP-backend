package tn.esprit.examen.EventManagement.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.EventManagement.dto.CommentDTO;
import tn.esprit.examen.EventManagement.dto.CommentMapper;
import tn.esprit.examen.EventManagement.entities.Comment;
import tn.esprit.examen.EventManagement.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add/{userId}/{eventId}")
    public CommentDTO addComment(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody String content) {
        Comment comment = commentService.addComment(userId, eventId, content);
        return CommentMapper.toDTO(comment);
    }

    @PutMapping("/update/{commentId}")
    public CommentDTO updateComment(@PathVariable Long commentId, @RequestBody String newContent) {
        Comment updated = commentService.updateComment(commentId, newContent);
        return CommentMapper.toDTO(updated);
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/event/{eventId}")
    public List<CommentDTO> getCommentsByEvent(@PathVariable Long eventId) {
        return commentService.getCommentsByEvent(eventId)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<CommentDTO> getCommentsByUser(@PathVariable Long userId) {
        return commentService.getCommentsByUser(userId)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }
}


