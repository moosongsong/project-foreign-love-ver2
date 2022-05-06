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
        this(Generators.timeBasedGenerator().generate(), title, content, user, imageUrl,
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null, null);
    }

    public FreeBoard(UUID id, String title, String content, User user, String imageUrl, LocalDateTime createdAt,
                     LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(id, title, content, user, imageUrl, createdAt, updatedAt, deletedAt);
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
