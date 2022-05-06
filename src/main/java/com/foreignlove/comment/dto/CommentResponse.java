package com.foreignlove.comment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponse(
    UUID id,
    String content,
    UserResponse user,
    LocalDateTime createdAt
) {
    public record UserResponse(
        UUID id,
        String nickname,
        String School,
        String nation
    ){}
}
