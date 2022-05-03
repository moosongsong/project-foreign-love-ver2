package com.foreignlove.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonRouterController {
    @GetMapping
    public String main(){
        return "common/main";
    }

    @GetMapping("/not-found")
    public String notfound(){
        return "common/404_not_found";
    }
}
