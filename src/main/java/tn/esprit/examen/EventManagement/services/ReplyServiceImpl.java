package tn.esprit.examen.EventManagement.services;


import org.springframework.stereotype.Service;
import tn.esprit.examen.EventManagement.entities.Comment;
import tn.esprit.examen.EventManagement.entities.Reply;
import tn.esprit.examen.EventManagement.entities.User;
import tn.esprit.examen.EventManagement.repositories.CommentRepository;
import tn.esprit.examen.EventManagement.repositories.IUserRepository;
import tn.esprit.examen.EventManagement.repositories.ReplyRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final IUserRepository userRepository;
    private final CommentRepository commentRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository, IUserRepository userRepository, CommentRepository commentRepository) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Reply addReply(Long userId, Long commentId, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        Reply reply = new Reply();
        reply.setAuthor(user);
        reply.setComment(comment);
        reply.setContent(content);
        reply.setCreatedAt(LocalDateTime.now());

        return replyRepository.save(reply);
    }

    @Override
    public Reply updateReply(Long replyId, String newContent) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("Reply not found"));
        reply.setContent(newContent);
        return replyRepository.save(reply);
    }

    @Override
    public void deleteReply(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Override
    public List<Reply> getRepliesByComment(Long commentId) {
        return replyRepository.findByCommentId(commentId);
    }

    @Override
    public List<Reply> getRepliesByUser(Long userId) {
        return replyRepository.findByAuthorId(userId);
    }
}

