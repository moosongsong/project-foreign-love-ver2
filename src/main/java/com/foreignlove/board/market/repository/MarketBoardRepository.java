package com.foreignlove.board.market.repository;

import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.model.MarketBoard;

import java.util.List;
import java.util.UUID;

public interface MarketBoardRepository {
    MarketBoard save(MarketBoard marketBoard);

    MarketBoard findById(UUID id);

    List<MarketBoard> findAll();

    List<MarketBoard> findAllByType(DealingType type);

    void findByIdAndUserId(UUID id, UUID userId);
}
