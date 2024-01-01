package backend.backend.domain.user.controller;

import backend.backend.common.jwt.TokenProvider;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponseDto> login(@RequestBody UserDto.LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(loginRequestDto, response));
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @return ResponseEntity<UserDto.LoginResponseDto>
     *
     * 클라이언트에서 로그인 후 새로고침 시
     * 유저 정보가 소멸되기 때문에 사라진 유저정보를 다시 돌리는 컨트롤러
     *
     */
    @PostMapping("/refreshData")
    public ResponseEntity<UserDto.LoginResponseDto> refreshData(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(userService.refreshData(request, response));
    }

}
