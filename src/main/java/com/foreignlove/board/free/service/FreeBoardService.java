package com.foreignlove.board.free.service;

import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FreeBoardService {
    FreeBoard add(String title, String content, MultipartFile file, User user);

    FreeBoard getById(UUID id);

    List<FreeBoard> getAll();
}
