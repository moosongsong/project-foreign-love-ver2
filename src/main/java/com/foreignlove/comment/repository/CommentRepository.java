package com.foreignlove.comment.repository;

import com.foreignlove.comment.model.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
}
