package com.foreignlove.board.market.service;

import com.foreignlove.board.market.dto.MarketBoardCreateResponse;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.model.MarketBoard;
import com.foreignlove.board.market.repository.MarketBoardRepository;
import com.foreignlove.infra.s3.service.S3FileIOManager;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SimpleMarketBoardService implements MarketBoardService {
    private final MarketBoardRepository marketBoardRepository;
    private final S3FileIOManager s3FileIOManager;

    @Override
    public MarketBoardCreateResponse add(String title, String content, User user, Integer cost, DealingType type,
                                         MultipartFile file) {
        String imageUrl = null;
        if (!file.isEmpty()) imageUrl = s3FileIOManager.uploadImage(file);
        MarketBoard marketBoard = new MarketBoard(title, content, user, imageUrl, cost, type);
        MarketBoard result = marketBoardRepository.save(marketBoard);
        return getCreateResponse(result);
    }

    private MarketBoardCreateResponse getCreateResponse(MarketBoard marketBoard) {
        User user = marketBoard.getUser();
        School school = user.getSchool();
        Nation nation = school.getNation();
        MarketBoardCreateResponse.UserResponse userResponse = new MarketBoardCreateResponse.UserResponse(user.getId(),
            user.getNickname());
        MarketBoardCreateResponse.SchoolResponse schoolResponse =
            new MarketBoardCreateResponse.SchoolResponse(school.getId(), school.getName());
        MarketBoardCreateResponse.NationResponse nationResponse =
            new MarketBoardCreateResponse.NationResponse(nation.getId(), nation.getName());
        return new MarketBoardCreateResponse(marketBoard.getId(), marketBoard.getTitle(), marketBoard.getContent(),
            userResponse, schoolResponse, nationResponse, marketBoard.getImageUrl(), marketBoard.getCost(),
            marketBoard.getType().toString(), marketBoard.getStep().toString(), marketBoard.getCreatedAt());
    }
}
