package com.foreignlove.board.market.service;

import com.foreignlove.board.market.dto.MarketBoardCreateResponse;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.user.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface MarketBoardService {
    MarketBoardCreateResponse add(String title, String content, User user, Integer cost, DealingType type,
                                  MultipartFile file);
}
