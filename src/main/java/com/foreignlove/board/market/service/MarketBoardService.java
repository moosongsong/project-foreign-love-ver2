package com.foreignlove.board.market.service;

import com.foreignlove.board.market.dto.MarketBoardCreateResponse;
import com.foreignlove.board.market.dto.MarketBoardDetailResponse;
import com.foreignlove.board.market.dto.MarketBoardListResponse;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MarketBoardService {
    MarketBoardCreateResponse add(String title, String content, User user, Integer cost, DealingType type,
                                  MultipartFile file);

    MarketBoardDetailResponse getById(UUID id);

    List<MarketBoardListResponse> getAll(String type);

    Boolean isMine(UUID id, User user);

    void removeById(UUID id);
}
