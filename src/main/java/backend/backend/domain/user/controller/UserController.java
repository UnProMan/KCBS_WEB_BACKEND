package backend.backend.domain.user.controller;

import backend.backend.common.jwt.TokenDto;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto.LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ddd");
    }

}
