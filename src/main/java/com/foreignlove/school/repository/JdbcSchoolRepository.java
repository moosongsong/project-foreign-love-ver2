package com.foreignlove.school.repository;

import com.foreignlove.common.exception.FindFailException;
import com.foreignlove.common.exception.SaveFailException;
import com.foreignlove.common.util.JdbcUtils;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
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
public class JdbcSchoolRepository implements SchoolRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public School save(School school) {
        int result = jdbcTemplate.update(
            "INSERT INTO schools(id, name, nation_id) " +
                "VALUES(UNHEX(REPLACE(:id, '-', '')), :name, UNHEX(REPLACE(:nation, '-', '')))",
            school.getParamMap()
        );
        if (result != 1) throw new SaveFailException();
        return school;
    }

    @Override
    public School findById(UUID id) {
        School school;
        try {
            school = jdbcTemplate.queryForObject(
                "SELECT schools.id AS 'id', schools.name AS 'name', " +
                    "schools.nation_id AS 'nation_id', nations.name AS 'nation_name' " +
                    "FROM schools LEFT JOIN nations on schools.nation_id = nations.id " +
                    "WHERE schools.id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString()), schoolRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException();
        }
        return school;
    }

    @Override
    public List<School> findAll() {
        return jdbcTemplate.query(
            "SELECT schools.id AS 'id', schools.name AS 'name', " +
            "schools.nation_id AS 'nation_id', nations.name AS 'nation_name' " +
            "FROM schools LEFT JOIN nations on schools.nation_id = nations.id ", schoolRowMapper);
    }

    private final RowMapper<School> schoolRowMapper = (rs, rowNum) -> {
        UUID id = JdbcUtils.toUUID(rs.getBytes("id"));
        String name = rs.getString("name");
        UUID nationId = JdbcUtils.toUUID(rs.getBytes("nation_id"));
        String nation_name = rs.getString("nation_name");
        Nation nation = new Nation(nationId, nation_name);
        return new School(id, nation, name);
    };
}
