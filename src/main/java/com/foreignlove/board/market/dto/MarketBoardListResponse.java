package com.foreignlove.board.market.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MarketBoardListResponse(
    UUID id,
    String title,
    UserResponse user,
    NationResponse nation,
    Integer cost,
    String type,
    String step,
    LocalDateTime createdAt
) {
    public record UserResponse(
        UUID id,
        String nickname
    ) {}
    public record NationResponse(
        UUID id,
        String name
    ) {}
}
