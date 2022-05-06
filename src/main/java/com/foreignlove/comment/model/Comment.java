package com.foreignlove.comment.model;

import com.fasterxml.uuid.Generators;
import com.foreignlove.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Comment {
    private final UUID id;
    private String content;
    private final UUID boardId;
    private final User user;
    private final LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public Comment(String content, UUID boardId, User user) {
        this(Generators.timeBasedGenerator().generate(), content, boardId, user,
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null);
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("content", content);
        map.put("boardId", boardId.toString());
        map.put("userId", user.getId().toString());
        map.put("createdAt", createdAt);
        map.put("deletedAt", deletedAt);
        return map;
    }
}
