package com.foreignlove.comment.dto;

import java.util.UUID;

public record CommentCreateRequest(
    UUID boardId,
    String content
) {}
