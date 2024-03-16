package backend.backend.domain.user.controller;

import backend.backend.domain.user.dto.UserAuthDto;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인 인증 인가 API
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDto.LoginResponse> login(@RequestBody UserAuthDto.LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(userAuthService.login(loginRequest, response));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userAuthService.logout(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @return ResponseEntity<UserDto.LoginResponse>
     *
     * 클라이언트에서 로그인 후 새로고침 시
     * 유저 정보가 소멸되기 때문에 사라진 유저정보를 다시 돌리는 컨트롤러
     *
     */
    @GetMapping("/refreshData")
    public ResponseEntity<UserAuthDto.LoginResponse> getRefreshData(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(userAuthService.refreshData(request, response));
    }

}
