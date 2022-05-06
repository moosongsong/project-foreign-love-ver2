package com.foreignlove.board.market.repository;

import com.foreignlove.board.market.model.DealingStep;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.model.MarketBoard;
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
public class JdbcMarketBoardRepository implements MarketBoardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public MarketBoard save(MarketBoard marketBoard) {
        int result = jdbcTemplate.update(
            "INSERT INTO market_board (id, title, content, user_id, image_url, cost, type, step, created_at, " +
                "updated_at, deleted_at) VALUES (UNHEX(REPLACE(:id, '-', '')), :title, :content, UNHEX(REPLACE" +
                "(:userId, '-', '')), :imageUrl, :cost, :type, :step, :createdAt, :updatedAt, :deletedAt);",
            marketBoard.getParamMap());
        if (result != 1) throw new SaveFailException();
        return marketBoard;
    }

    @Override
    public List<MarketBoard> findAll() {
        return jdbcTemplate.query("SELECT * FROM market_board_view WHERE deleted_at IS NULL", marketBoardRowMapper);
    }

    @Override
    public List<MarketBoard> findAllByType(DealingType type) {
        return jdbcTemplate.query("SELECT * FROM market_board_view WHERE type LIKE :type AND deleted_at IS NULL",
            Collections.singletonMap("type", type.toString()), marketBoardRowMapper);
    }

    private final RowMapper<MarketBoard> marketBoardRowMapper = (rs, rowNum) -> {
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
        Integer cost = rs.getInt("cost");
        String type = rs.getString("type");
        String step = rs.getString("step");
        LocalDateTime createdAt = toLocaleDateTime(rs.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocaleDateTime(rs.getTimestamp("updated_at"));
        LocalDateTime deletedAt = toLocaleDateTime(rs.getTimestamp("deleted_at"));

        return new MarketBoard(id, title, content, user, imageUrl, cost, DealingType.valueOf(type),
            DealingStep.valueOf(step), createdAt, updatedAt, deletedAt);
    };
}
