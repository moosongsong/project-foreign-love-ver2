package com.foreignlove.comment.controller;

import com.foreignlove.comment.dto.CommentCreateRequest;
import com.foreignlove.comment.dto.CommentResponse;
import com.foreignlove.comment.service.CommentService;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class V1RestCommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> postComment(@RequestBody CommentCreateRequest request, HttpSession session) {
        CommentResponse comment = commentService.add(request.boardId(), request.content(),
            (User) session.getAttribute("user"));
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> list(@PathVariable UUID id) {
        List<CommentResponse> comments = commentService.getAllByBoardId(id);
        return ResponseEntity.ok().body(comments);
    }
}
