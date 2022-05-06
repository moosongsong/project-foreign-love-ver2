package com.foreignlove.school.model;

import com.fasterxml.uuid.Generators;
import com.foreignlove.nation.model.Nation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class School {
    private final UUID id;
    private final Nation nation;
    private final String name;

    public School(Nation nation, String name) {
        this.id = Generators.timeBasedGenerator().generate();
        this.nation = nation;
        this.name = name;
    }

    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id.toString());
        map.put("name", name);
        map.put("nation", nation.toString());
        return map;
    }

    public Response getResponse() {
        return new Response(id, name, nation.getResponse());
    }

    public record Request(
        UUID id,
        String name
    ) {}

    public record Response(
        UUID id,
        String name,
        Nation.Response nation
    ) {}

    @Override
    public String toString() {
        return id.toString();
    }
}
