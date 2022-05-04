package com.foreignlove.board.free.repository;

import com.foreignlove.board.free.model.FreeBoard;

import java.util.List;
import java.util.UUID;

public interface FreeBoardRepository {
    FreeBoard save(FreeBoard freeBoard);

    FreeBoard findById(UUID id);

    FreeBoard findByIdAndUserId(UUID id, UUID userId);

    List<FreeBoard> findAll();
}
