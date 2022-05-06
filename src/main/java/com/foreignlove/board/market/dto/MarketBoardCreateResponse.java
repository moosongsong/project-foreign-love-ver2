package com.foreignlove.board.market.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MarketBoardCreateResponse(
    UUID id,
    String title,
    String content,
    UserResponse user,
    SchoolResponse school,
    NationResponse nation,
    String imageUrl,
    Integer cost,
    String type,
    String step,
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
