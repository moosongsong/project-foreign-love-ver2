package com.foreignlove.nation.repository;

import com.foreignlove.nation.model.Nation;

import java.util.List;
import java.util.UUID;

public interface NationRepository {
    Nation save(Nation nation);

    Nation findById(UUID id);

    Nation findByName(String name);

    List<Nation> findAll();
}
