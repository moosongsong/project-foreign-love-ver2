package com.foreignlove.board.market.controller;

import com.foreignlove.board.market.dto.MarketBoardCreateResponse;
import com.foreignlove.board.market.dto.MarketBoardDetailResponse;
import com.foreignlove.board.market.dto.MarketBoardListResponse;
import com.foreignlove.board.market.model.DealingType;
import com.foreignlove.board.market.service.MarketBoardService;
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
@RequestMapping("/api/v1/market")
public class V1RestMarketBoardController {
    private final MarketBoardService marketBoardService;

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(required = false) String type) {
        List<MarketBoardListResponse> list = marketBoardService.getAll(type);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestPart String title, @RequestPart String content,
                                      @RequestPart String cost, @RequestPart String type,
                                      @RequestPart MultipartFile file, HttpSession session) {
        MarketBoardCreateResponse response = marketBoardService.add(title, content,
            (User) session.getAttribute("user"), Integer.parseInt(cost), DealingType.valueOf(type), file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> detail(@PathVariable UUID id) {
        MarketBoardDetailResponse response = marketBoardService.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        // TODO 사용자 검증 필요
        marketBoardService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<Object> isMine(@PathVariable UUID id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.ok(false);
        Boolean response = marketBoardService.isMine(id, user);
        return ResponseEntity.ok(response);
    }
}
