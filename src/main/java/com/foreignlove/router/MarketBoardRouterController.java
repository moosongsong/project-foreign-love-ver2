package com.foreignlove.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/market")
public class MarketBoardRouterController {
    @GetMapping
    public String listPage() {
        return "market/market_list";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") UUID id) {
        return "market/market_detail";
    }

    @GetMapping("/post")
    public String postPage() {
        return "market/market_add";
    }
}
