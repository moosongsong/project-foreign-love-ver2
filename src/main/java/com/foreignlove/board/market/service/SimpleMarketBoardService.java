package com.foreignlove.board.market.service;

import com.foreignlove.board.market.dto.MarketBoardCreateResponse;
import com.foreignlove.board.market.dto.MarketBoardDetailResponse;
import com.foreignlove.board.market.dto.MarketBoardListResponse;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.model.MarketBoard;
import com.foreignlove.board.market.repository.MarketBoardRepository;
import com.foreignlove.common.exception.FindFailException;
import com.foreignlove.infra.s3.service.S3FileIOManager;
import com.foreignlove.nation.model.Nation;
import com.foreignlove.school.model.School;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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

    @Override
    public MarketBoardDetailResponse getById(UUID id) {
        MarketBoard result = marketBoardRepository.findById(id);
        return getDetailResponse(result);
    }

    @Override
    public List<MarketBoardListResponse> getAll(String type) {
        List<MarketBoard> list;
        try {
            list = marketBoardRepository.findAllByType(DealingType.valueOf(type));
        } catch (Exception e) {
            list = marketBoardRepository.findAll();
        }
        return list.stream().map(this::getListResponse).toList();
    }

    @Override
    public Boolean isMine(UUID id, User user) {
        try {
            marketBoardRepository.findByIdAndUserId(id, user.getId());
        } catch (FindFailException e) {
            return false;
        }
        return true;
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

    private MarketBoardListResponse getListResponse(MarketBoard marketBoard) {
        User user = marketBoard.getUser();
        Nation nation = user.getSchool().getNation();
        MarketBoardListResponse.UserResponse userResponse = new MarketBoardListResponse.UserResponse(user.getId(),
            user.getNickname());
        MarketBoardListResponse.NationResponse nationResponse =
            new MarketBoardListResponse.NationResponse(nation.getId(), nation.getName());
        return new MarketBoardListResponse(marketBoard.getId(), marketBoard.getTitle(),
            userResponse, nationResponse, marketBoard.getCost(), marketBoard.getType().toString(),
            marketBoard.getStep().toString(), marketBoard.getCreatedAt());
    }

    private MarketBoardDetailResponse getDetailResponse(MarketBoard marketBoard) {
        User user = marketBoard.getUser();
        School school = user.getSchool();
        Nation nation = school.getNation();
        MarketBoardDetailResponse.UserResponse userResponse = new MarketBoardDetailResponse.UserResponse(user.getId(),
            user.getNickname());
        MarketBoardDetailResponse.SchoolResponse schoolResponse =
            new MarketBoardDetailResponse.SchoolResponse(school.getId(), school.getName());
        MarketBoardDetailResponse.NationResponse nationResponse =
            new MarketBoardDetailResponse.NationResponse(nation.getId(), nation.getName());
        return new MarketBoardDetailResponse(marketBoard.getId(), marketBoard.getTitle(), marketBoard.getContent(),
            userResponse, schoolResponse, nationResponse, marketBoard.getImageUrl(), marketBoard.getCost(),
            marketBoard.getType().toString(), marketBoard.getStep().toString(), marketBoard.getCreatedAt());
    }
}
