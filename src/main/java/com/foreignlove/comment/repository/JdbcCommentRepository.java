package com.foreignlove.comment.repository;

import com.foreignlove.comment.model.Comment;
import com.foreignlove.common.exception.SaveFailException;
import com.foreignlove.common.util.JdbcUtils;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
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
public class JdbcCommentRepository implements CommentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Comment save(Comment comment) {
        int result = jdbcTemplate.update(
            "INSERT INTO comments(id, content, board_id, user_id, created_at, deleted_at) " +
                "VALUES(UNHEX(REPLACE(:id, '-', '')), :content, UNHEX(REPLACE(:boardId, '-', '')), " +
                "UNHEX(REPLACE(:userId, '-', '')), :createdAt, :deletedAt)",
            comment.getParamMap()
        );
        if (result != 1) throw new SaveFailException();
        return comment;
    }

    @Override
    public List<Comment> findAllByBoardId(UUID boardId) {
        return jdbcTemplate.query("SELECT * from comments_view WHERE deleted_at IS NULL AND board_id = UNHEX(REPLACE(:boardId, '-', ''))",
            Collections.singletonMap("boardId", boardId.toString()),
            commentRowMapper);
    }

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
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
        UUID boardId = JdbcUtils.toUUID(rs.getBytes("board_id"));
        String content = rs.getString("content");
        LocalDateTime createdAt = toLocaleDateTime(rs.getTimestamp("created_at"));
        LocalDateTime deletedAt = toLocaleDateTime(rs.getTimestamp("deleted_at"));

        return new Comment(id, content, boardId, user, createdAt, deletedAt);
    };
}
