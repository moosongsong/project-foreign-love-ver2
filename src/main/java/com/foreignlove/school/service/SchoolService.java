package com.foreignlove.school.service;

import com.foreignlove.school.model.School;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    School add(UUID nationId, String name);

    School getById(UUID id);

    List<School> getAll();
}
