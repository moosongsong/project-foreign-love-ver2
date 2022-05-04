package com.foreignlove.board.free.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FreeBoardDetailResponse(
    UUID id,
    String title,
    String content,
    UserResponse user,
    SchoolResponse school,
    NationResponse nation,
    String imageUrl,
    LocalDateTime createdAt
) {
    public record UserResponse(
        UUID id,
        String nickname
    ) {}
    public record SchoolResponse(
        UUID id,
        String name
    ) {}
    public record NationResponse(
        UUID id,
        String name
    ) {}
}
