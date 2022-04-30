package com.foreignlove.user.model;

import com.fasterxml.uuid.Generators;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String email;
    private final String name;
    private String password;
    private final School school;
    private String nickname;
    private String imageUrl;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    public User(String email, String name, String password, School school, String nickname) {
        this(Generators.timeBasedGenerator().generate(), email, name, password, school, nickname, null,
            LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null, null);
    }

    public User(UUID id, String email, String name, String password, School school, String nickname, String imageUrl,
                LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.school = school;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("email", email);
        map.put("name", name);
        map.put("password", password);
        map.put("school", school.toString());
        map.put("nickname", nickname);
        map.put("imageUrl", imageUrl);
        map.put("createdAt", createdAt);
        map.put("updatedAt", updatedAt);
        map.put("deletedAt", deletedAt);
        return map;
    }

    public Response getResponse() {
        return new Response(id, email, name, password, school.getResponse(), nickname, imageUrl, createdAt);
    }

    public record LoginRequest(
        String email,
        String password
    ) {}

    public record Request(
        String email,
        String name,
        String password,
        UUID schoolId,
        String nickname
    ) {}

    public record Response(
        UUID id,
        String email,
        String name,
        String password,
        School.Response school,
        String nickname,
        String imageUrl,
        LocalDateTime createdAt
    ) {}
}
