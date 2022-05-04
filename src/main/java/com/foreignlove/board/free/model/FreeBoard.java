package com.foreignlove.board.free.model;

import com.fasterxml.uuid.Generators;
import com.foreignlove.board.model.Board;
import com.foreignlove.user.model.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class FreeBoard extends Board {
    public FreeBoard(String title, String content, String imageUrl, User user) {
        super(Generators.timeBasedGenerator().generate(), user, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
            null);
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public FreeBoard(UUID id, String title, String content, User user, String imageUrl, LocalDateTime createdAt,
                     LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(id, user, createdAt, deletedAt);
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.updatedAt = updatedAt;
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("title", title);
        map.put("content", content);
        map.put("userId", user.getId().toString());
        map.put("imageUrl", imageUrl);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("deletedAt", deletedAt);
        return map;
    }

    public Response getResponse() {
        return new Response(id, title, content, user.getResponse(), imageUrl, createdAt);
    }

    public record Response(
        UUID id,
        String title,
        String content,
        User.Response user,
        String imageUrl,
        LocalDateTime createdAt
    ) {}
}
