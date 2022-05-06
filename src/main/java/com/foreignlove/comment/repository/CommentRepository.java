package com.foreignlove.comment.repository;

import com.foreignlove.comment.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);

    List<Comment> findAllByBoardId(UUID boardId);
}
