package backend.backend.domain.user.controller;

import backend.backend.common.jwt.TokenDto;
import backend.backend.domain.user.dto.PrincipalUser;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.service.UserService;
import backend.backend.system.annotation.role.AdminAuthorize;
import backend.backend.system.annotation.role.LeaderAuthorize;
import backend.backend.system.annotation.role.PresidentAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto.LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal PrincipalUser user) {
        return ResponseEntity.ok("ddd");
    }

}
