package tn.esprit.examen.EventManagement.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.EventManagement.dto.ReplyDTO;
import tn.esprit.examen.EventManagement.dto.ReplyMapper;
import tn.esprit.examen.EventManagement.entities.Reply;
import tn.esprit.examen.EventManagement.services.ReplyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/add/{userId}/{commentId}")
    public ReplyDTO addReply(@PathVariable Long userId, @PathVariable Long commentId, @RequestBody String content) {
        Reply reply = replyService.addReply(userId, commentId, content);
        return ReplyMapper.toDTO(reply);
    }

    @PutMapping("/update/{replyId}")
    public ReplyDTO updateReply(@PathVariable Long replyId, @RequestBody String newContent) {
        Reply updated = replyService.updateReply(replyId, newContent);
        return ReplyMapper.toDTO(updated);
    }

    @DeleteMapping("/delete/{replyId}")
    public void deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
    }

    @GetMapping("/comment/{commentId}")
    public List<ReplyDTO> getRepliesByComment(@PathVariable Long commentId) {
        return replyService.getRepliesByComment(commentId)
                .stream()
                .map(ReplyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<ReplyDTO> getRepliesByUser(@PathVariable Long userId) {
        return replyService.getRepliesByUser(userId)
                .stream()
                .map(ReplyMapper::toDTO)
                .collect(Collectors.toList());
    }
}


