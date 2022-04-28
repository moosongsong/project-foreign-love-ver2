package com.foreignlove.school.controller.api;

import com.foreignlove.school.model.School;
import com.foreignlove.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schools")
public class V1RestSchoolController {
    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<Object> list() {
        List<School.Response> list = schoolService.getAll().stream().map(School::getResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody School.Request request) {
        School school = schoolService.add(request.id(), request.name());
        return ResponseEntity.ok(school.getResponse());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Optional<UUID> id) {
        School school = id.map(schoolService::getById).orElseThrow();
        return ResponseEntity.ok(school.getResponse());
    }
}
