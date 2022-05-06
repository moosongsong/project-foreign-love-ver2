package com.foreignlove.board.market.model;

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
public class MarketBoard extends Board {
    private Integer cost;
    private DealingType type;
    private DealingStep step;

    public MarketBoard(String title, String content, User user, String imageUrl, Integer cost, DealingType type) {
        this(Generators.timeBasedGenerator().generate(), title, content, user, imageUrl, cost, type,
            DealingStep.WAITING, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null, null);
    }

    public MarketBoard(UUID id, String title, String content, User user, String imageUrl, Integer cost,
                       DealingType type, DealingStep step, LocalDateTime createdAt, LocalDateTime updatedAt,
                       LocalDateTime deletedAt) {
        super(id, title, content, user, imageUrl, createdAt, updatedAt, deletedAt);
        this.cost = cost;
        this.type = type;
        this.step = step;
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("title", title);
        map.put("content", content);
        map.put("userId", user.getId().toString());
        map.put("imageUrl", imageUrl);
        map.put("cost", cost);
        map.put("type", type.toString());
        map.put("step", step.toString());
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("deletedAt", deletedAt);
        return map;
    }
}
