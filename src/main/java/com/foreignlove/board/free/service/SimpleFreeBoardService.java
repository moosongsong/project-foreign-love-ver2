package com.foreignlove.board.free.service;

import com.foreignlove.board.free.dto.FreeBoardDetailResponse;
import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.board.free.repository.FreeBoardRepository;
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
public class SimpleFreeBoardService implements FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final S3FileIOManager s3FileIOManager;

    @Override
    public FreeBoard add(String title, String content, MultipartFile file, User user) {
        String imageUrl = null;
        if (!file.isEmpty()) imageUrl = s3FileIOManager.uploadImage(file);
        FreeBoard freeBoard = new FreeBoard(title, content, imageUrl, user);
        return freeBoardRepository.save(freeBoard);
    }

    @Override
    public FreeBoardDetailResponse getById(UUID id) {
        FreeBoard freeBoard = freeBoardRepository.findById(id);
        return getDetailResponseFrom(freeBoard);
    }

    @Override
    public List<FreeBoard> getAll() {
        return freeBoardRepository.findAll();
    }

    public FreeBoardDetailResponse getDetailResponseFrom(FreeBoard freeBoard) {
        User user = freeBoard.getUser();
        School school = user.getSchool();
        Nation nation = school.getNation();
        FreeBoardDetailResponse.UserResponse userResponse = new FreeBoardDetailResponse.UserResponse(user.getId(),
            user.getNickname());
        FreeBoardDetailResponse.SchoolResponse schoolResponse =
            new FreeBoardDetailResponse.SchoolResponse(school.getId(), school.getName());
        FreeBoardDetailResponse.NationResponse nationResponse =
            new FreeBoardDetailResponse.NationResponse(nation.getId(), nation.getName());
        return new FreeBoardDetailResponse(freeBoard.getId(), freeBoard.getTitle(), freeBoard.getContent(),
            userResponse, schoolResponse, nationResponse, freeBoard.getImageUrl(), freeBoard.getCreatedAt());
    }
}
