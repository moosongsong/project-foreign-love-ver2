package com.foreignlove.nation.service;

import com.foreignlove.nation.model.Nation;

import java.util.List;
import java.util.UUID;

public interface NationService {
    Nation add(String name);

    Nation getById(UUID id);

    Nation getByName(String name);

    List<Nation> getAll();
}
