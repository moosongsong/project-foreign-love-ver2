package com.foreignlove.nation.controller.api;

import com.foreignlove.nation.model.Nation;
import com.foreignlove.nation.service.NationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/nations")
public class V1RestNationController {
    private final NationService nationService;

    @GetMapping
    public ResponseEntity<Object> nationList() {
        List<Nation.Response> list = nationService.getAll().stream().map(Nation::getResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Object> nationSave(@RequestBody Nation.Request request) {
        Nation nation = nationService.add(request.name());
        return ResponseEntity.ok(nation.getResponse());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> nationDetail(@PathVariable("id") Optional<UUID> id) {
        Nation nation = id.map(nationService::getById).orElseThrow();
        return ResponseEntity.ok(nation.getResponse());
    }
}
