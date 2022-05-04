package com.foreignlove.board.free.service;

import com.foreignlove.board.free.dto.FreeBoardDetailResponse;
import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FreeBoardService {
    FreeBoard add(String title, String content, MultipartFile file, User user);

    FreeBoardDetailResponse getById(UUID id);

    Boolean isMine(UUID id, User user);

    List<FreeBoard> getAll();

    void remove(UUID id, User user);
}
