package com.foreignlove.board.model;

import com.foreignlove.user.model.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Board {
    protected final UUID id;
    protected String title;
    protected String content;
    protected final User user;
    protected String imageUrl;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected final LocalDateTime deletedAt;

    protected Board(UUID id, String title, String content, User user, String imageUrl, LocalDateTime createdAt,
                 LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    protected Board(UUID id, User user, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
