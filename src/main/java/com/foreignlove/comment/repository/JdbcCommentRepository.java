package com.foreignlove.comment.repository;

import com.foreignlove.comment.model.Comment;
import com.foreignlove.common.exception.SaveFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
