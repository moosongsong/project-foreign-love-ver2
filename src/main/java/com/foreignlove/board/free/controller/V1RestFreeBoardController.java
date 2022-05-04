package com.foreignlove.board.free.controller;

import com.foreignlove.board.free.dto.FreeBoardDetailResponse;
import com.foreignlove.board.free.model.FreeBoard;
import com.foreignlove.board.free.service.FreeBoardService;
import com.foreignlove.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/free")
public class V1RestFreeBoardController {
    private final FreeBoardService freeBoardService;

    @GetMapping
    public ResponseEntity<Object> list() {
        List<FreeBoard.Response> list = freeBoardService.getAll().stream().map(FreeBoard::getResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") UUID id) {
        FreeBoardDetailResponse response = freeBoardService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestPart String title, @RequestPart String content,
                                      @RequestPart MultipartFile file, HttpSession session) {
        FreeBoard.Response response = freeBoardService.add(title, content, file, (User) session.getAttribute("user"))
            .getResponse();
        return ResponseEntity.ok(response);
    }
}
