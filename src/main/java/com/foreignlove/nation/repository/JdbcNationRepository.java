package com.foreignlove.nation.repository;

import com.foreignlove.common.exception.FindFailException;
import com.foreignlove.common.exception.SaveFailException;
import com.foreignlove.common.util.JdbcUtils;
import com.foreignlove.nation.model.Nation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JdbcNationRepository implements NationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Nation save(Nation nation) {
        int result = jdbcTemplate.update(
            "INSERT INTO nations(id, name) VALUES(UNHEX(REPLACE(:id, '-', '')), :name)", nation.getParamMap());
        if (result != 1) throw new SaveFailException();
        return nation;
    }

    @Override
    public Nation findById(UUID id) {
        Nation nation;
        try {
            nation = jdbcTemplate.queryForObject(
                "SELECT * FROM nations WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString()), nationRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException("ID에 해당하는 국가가 없습니다.");
        }
        return nation;
    }

    @Override
    public Nation findByName(String name) {
        Nation nation;
        try {
            nation = jdbcTemplate.queryForObject(
                "SELECT * FROM nations WHERE name LIKE :name",
                Collections.singletonMap("name", name), nationRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException("이름에 해당하는 국가가 없습니다.");
        }
        return nation;
    }

    @Override
    public List<Nation> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM nations", nationRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException("목록을 조회할 수 없습니다.");
        }
    }

    private final RowMapper<Nation> nationRowMapper = (rs, rowNum) -> {
        UUID id = JdbcUtils.toUUID(rs.getBytes("id"));
        String name = rs.getString("name");
        return new Nation(id, name);
    };
}
