package com.foreignlove.school.repository;

import com.foreignlove.school.model.School;

import java.util.List;
import java.util.UUID;

public interface SchoolRepository {
    School save(School school);

    School findById(UUID id);

    List<School> findAll();
}
