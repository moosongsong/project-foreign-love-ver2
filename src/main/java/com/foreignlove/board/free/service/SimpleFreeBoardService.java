package com.foreignlove.board.free.service;

import com.foreignlove.board.free.dto.FreeBoardCreateRequest;
import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.board.free.repository.FreeBoardRepository;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleFreeBoardService implements FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    @Override
    public FreeBoard add(FreeBoardCreateRequest request, User user) {
        FreeBoard freeBoard = new FreeBoard(request.title(), request.content(), user);
        return freeBoardRepository.save(freeBoard);
    }

    @Override
    public FreeBoard getById(UUID id) {
        return freeBoardRepository.findById(id);
    }

    @Override
    public List<FreeBoard> getAll() {
        return freeBoardRepository.findAll();
    }
}
