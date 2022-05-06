package com.foreignlove.board.market.repository;

import com.foreignlove.board.market.model.MarketBoard;
import com.foreignlove.common.exception.SaveFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
