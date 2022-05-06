package com.foreignlove.comment.service;


import com.foreignlove.comment.dto.CommentResponse;
import com.foreignlove.user.model.User;

import java.util.UUID;

public interface CommentService {
    CommentResponse add(UUID boardId, String comment, User user);
}
