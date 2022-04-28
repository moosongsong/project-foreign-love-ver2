package com.foreignlove.school.service;

import com.foreignlove.nation.model.Nation;
import com.foreignlove.nation.repository.NationRepository;
import com.foreignlove.school.model.School;
import com.foreignlove.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleSchoolService implements SchoolService {
    private final SchoolRepository schoolRepository;
    private final NationRepository nationRepository;

    @Override
    public School add(UUID nationId, String name) {
        Nation nation = nationRepository.findById(nationId);
        return schoolRepository.save(new School(nation, name));
    }

    @Override
    public School getById(UUID id) {
        return schoolRepository.findById(id);
    }

    @Override
    public List<School> getAll() {
        return schoolRepository.findAll();
    }
}
