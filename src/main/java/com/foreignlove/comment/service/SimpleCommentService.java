package com.foreignlove.comment.service;

import com.foreignlove.comment.dto.CommentResponse;
import com.foreignlove.comment.model.Comment;
import com.foreignlove.comment.repository.CommentRepository;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse add(UUID boardId, String comment, User user) {
        Comment newComment = new Comment(comment, boardId, user);
        Comment result = commentRepository.save(newComment);
        return getResponseFrom(result);
    }

    @Override
    public List<CommentResponse> getAllByBoardId(UUID boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return comments.stream().map(this::getResponseFrom).toList();
    }

    private CommentResponse getResponseFrom(Comment comment) {
        User user = comment.getUser();
        CommentResponse.UserResponse userResponse = new CommentResponse.UserResponse(user.getId(), user.getImageUrl(),
            user.getNickname(), user.getSchool().getName(), user.getSchool().getNation().getName());
        return new CommentResponse(comment.getId(), comment.getContent(), userResponse,
            comment.getCreatedAt());
    }
}
