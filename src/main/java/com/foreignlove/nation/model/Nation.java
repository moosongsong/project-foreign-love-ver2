package com.foreignlove.nation.model;

import com.fasterxml.uuid.Generators;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Nation {
    private final UUID id;
    private final String name;

    public Nation(String name) {
        this(Generators.timeBasedGenerator().generate(), name);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("name", name);
        return map;
    }

    public Response getResponse() {
        return new Response(id, name);
    }

    public record Request(
        String name
    ) {}

    public record Response(
        UUID id,
        String name
    ) {}
}
