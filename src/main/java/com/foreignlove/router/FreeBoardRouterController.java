package com.foreignlove.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/free")
public class FreeBoardRouterController {
    @GetMapping
    public String listPage() {
        return "free/free_list";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable("id") UUID id) {
        return "free/free_detail";
    }

    @GetMapping("/post")
    public String postPage() {
        return "free/free_add";
    }
}
