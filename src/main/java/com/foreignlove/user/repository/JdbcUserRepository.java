package com.foreignlove.user.repository;

import com.foreignlove.common.exception.FindFailException;
import com.foreignlove.common.exception.SaveFailException;
import com.foreignlove.common.util.JdbcUtils;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
import com.foreignlove.user.exception.LoginFailException;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.foreignlove.common.util.JdbcUtils.toLocaleDateTime;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        int result = jdbcTemplate.update(
            "INSERT INTO users VALUES (UNHEX(REPLACE(:id, '-', '')), :email, :name, PASSWORD(:password), " +
                "UNHEX(REPLACE(:school, '-', '')), :nickname, :imageUrl, :createdAt, :updatedAt, :deletedAt)",
            user.getParamMap());
        if (result != 1) throw new SaveFailException();
        return user;
    }

    @Override
    public User findById(UUID id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(
                "SELECT * FROM users_view WHERE id = :id",
                Collections.singletonMap("id", id.toString()), userRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException();
        }
        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user;
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        try {
            user = jdbcTemplate.queryForObject(
                "SELECT * FROM users_view WHERE email = :email and password = PASSWORD(:password)", map, userRowMapper);
        } catch (DataAccessException e) {
            throw new LoginFailException();
        }
        return user;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        UUID nationId = JdbcUtils.toUUID(rs.getBytes("nation_id"));
        String nationName = rs.getString("nation_name");
        Nation nation = new Nation(nationId, nationName);

        UUID schoolId = JdbcUtils.toUUID(rs.getBytes("school_id"));
        String schoolName = rs.getString("school_name");
        School school = new School(schoolId, nation, schoolName);

        UUID id = JdbcUtils.toUUID(rs.getBytes("id"));
        String email = rs.getString("email");
        String name = rs.getString("name");
        String password = rs.getString("password");
        String nickname = rs.getString("nickname");
        String imageUrl = rs.getString("image_url");
        LocalDateTime createdAt = toLocaleDateTime(rs.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocaleDateTime(rs.getTimestamp("updated_at"));
        LocalDateTime deletedAt = toLocaleDateTime(rs.getTimestamp("deleted_at"));
        return new User(id, email, name, password, school, nickname, imageUrl, createdAt, updatedAt, deletedAt);
    };
}
