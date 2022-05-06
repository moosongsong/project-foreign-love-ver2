package com.foreignlove.board.market.repository;

import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.model.MarketBoard;

import java.util.List;

public interface MarketBoardRepository {
    MarketBoard save(MarketBoard marketBoard);

    List<MarketBoard> findAll();

    List<MarketBoard> findAllByType(DealingType type);
}
