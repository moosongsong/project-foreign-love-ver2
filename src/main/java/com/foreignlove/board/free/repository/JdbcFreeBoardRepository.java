package com.foreignlove.board.free.repository;

import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.common.exception.FindFailException;
import com.foreignlove.common.exception.SaveFailException;
import com.foreignlove.common.util.JdbcUtils;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.foreignlove.common.util.JdbcUtils.toLocaleDateTime;

@Repository
@RequiredArgsConstructor
public class JdbcFreeBoardRepository implements FreeBoardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public FreeBoard save(FreeBoard freeBoard) {
        int result = jdbcTemplate.update(
            "INSERT INTO free_board(id, title, content,user_id, image_url, created_at, updated_at, deleted_at) " +
                "VALUES(UNHEX(REPLACE(:id, '-', '')), :title, :content, UNHEX(REPLACE(:userId, '-', '')), :imageUrl, :createdAt, :updatedAt, :deletedAt)",
            freeBoard.getParamMap()
        );
        if (result != 1) throw new SaveFailException();
        return freeBoard;
    }

    @Override
    public FreeBoard findById(UUID id) {
        FreeBoard freeBoard;
        try {
            freeBoard = jdbcTemplate.queryForObject(
                "SELECT * from freeboard_view WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString()), freeBoardRowMapper);
        } catch (DataAccessException e) {
            throw new FindFailException();
        }
        return freeBoard;
    }

    @Override
    public List<FreeBoard> findAll() {
        return jdbcTemplate.query("SELECT * from freeboard_view ORDER BY created_at DESC", freeBoardRowMapper);
    }

    private final RowMapper<FreeBoard> freeBoardRowMapper = (rs, rowNum) -> {
        UUID nationId = JdbcUtils.toUUID(rs.getBytes("nation_id"));
        String nationName = rs.getString("nation_name");
        Nation nation = new Nation(nationId, nationName);

        UUID schoolId = JdbcUtils.toUUID(rs.getBytes("school_id"));
        String schoolName = rs.getString("school_name");
        School school = new School(schoolId, nation, schoolName);

        UUID userId = JdbcUtils.toUUID(rs.getBytes("user_id"));
        String userEmail = rs.getString("user_email");
        String userName = rs.getString("user_name");
        String userPassword = rs.getString("user_password");
        String userNickname = rs.getString("user_nickname");
        String userImageUrl = rs.getString("user_image_url");
        LocalDateTime userCreatedAt = toLocaleDateTime(rs.getTimestamp("user_created_at"));
        LocalDateTime userUpdatedAt = toLocaleDateTime(rs.getTimestamp("user_updated_at"));
        LocalDateTime userDeletedAt = toLocaleDateTime(rs.getTimestamp("user_deleted_at"));
        User user = new User(userId, userEmail, userName, userPassword, school, userNickname, userImageUrl,
            userCreatedAt, userUpdatedAt, userDeletedAt);

        UUID id = JdbcUtils.toUUID(rs.getBytes("id"));
        String title = rs.getString("title");
        String content = rs.getString("content");
        String imageUrl = rs.getString("image_url");
        LocalDateTime createdAt = toLocaleDateTime(rs.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocaleDateTime(rs.getTimestamp("updated_at"));
        LocalDateTime deletedAt = toLocaleDateTime(rs.getTimestamp("deleted_at"));

        return new FreeBoard(id, title, content, user, imageUrl, createdAt, updatedAt, deletedAt);
    };
}
