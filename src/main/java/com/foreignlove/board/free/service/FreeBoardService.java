package com.foreignlove.board.free.service;

import com.foreignlove.board.free.dto.FreeBoardCreateRequest;
import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.user.model.User;

import java.util.List;
import java.util.UUID;

public interface FreeBoardService {
    FreeBoard add(FreeBoardCreateRequest request, User user);

    FreeBoard getById(UUID id);

    List<FreeBoard> getAll();
}
