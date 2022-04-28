package com.foreignlove.nation.service;

import com.foreignlove.nation.model.Nation;
import com.foreignlove.nation.repository.NationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleNationService implements NationService {
    private final NationRepository nationRepository;

    @Override
    public Nation add(String name) {
        Nation nation = new Nation(name);
        return nationRepository.save(nation);
    }

    @Override
    public Nation getById(UUID id) {
        return nationRepository.findById(id);
    }

    @Override
    public Nation getByName(String name) {
        return nationRepository.findByName(name);
    }

    @Override
    public List<Nation> getAll() {
        return nationRepository.findAll();
    }
}
