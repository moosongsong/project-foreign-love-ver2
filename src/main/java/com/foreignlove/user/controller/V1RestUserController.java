package com.foreignlove.user.controller;

import com.foreignlove.user.dto.UserLoginRequest;
import com.foreignlove.user.dto.UserRegisterRequest;
import com.foreignlove.user.model.User;
import com.foreignlove.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class V1RestUserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Optional<UserRegisterRequest> request) {
        User user = request.map(userService::add).orElseThrow();
        return ResponseEntity.ok(user.getResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Optional<UUID> id) {
        User user = id.map(userService::getById).orElseThrow();
        return ResponseEntity.ok(user.getResponse());
    }

    @GetMapping("/login")
    public ResponseEntity<Object> getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) return ResponseEntity.ok(user.getResponse());
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Optional<UserLoginRequest> request, HttpSession session) {
        User old = (User) session.getAttribute("user");
        if (old != null) return ResponseEntity.ok(old.getResponse());
        User user = request.map(userService::login).orElseThrow();
        session.setAttribute("user", user);
        return ResponseEntity.ok(user.getResponse());
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) {
        session.setAttribute("user", null);
        return ResponseEntity.ok().build();
    }
}
