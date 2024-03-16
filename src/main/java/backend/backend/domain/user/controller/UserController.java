package backend.backend.domain.user.controller;

import backend.backend.common.drive.DriveUtils;
import backend.backend.domain.user.entity.User;
import backend.backend.system.security.jwt.TokenProvider;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@RequestBody UserDto.LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(loginRequest, response));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
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
    public ResponseEntity<UserDto.LoginResponse> getRefreshData(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(userService.refreshData(request, response));
    }


    @PostMapping("/test/upload")
    public void dddd(@RequestParam("file") MultipartFile file) throws GeneralSecurityException, IOException {
        DriveUtils.uploadFile(file);
    }

    /**
     *
     * @param searchName 검색할 이름
     * @return KCBS 인원들 정보
     *
     * kcbs 인원현황 정보들을 전달하는 컨트롤러
     *
     */
    @GetMapping
    public ResponseEntity<List<UserDto.UsersResponse>> getUsers(@RequestParam String searchName,
                                                                    @RequestParam(required = false) Integer department) {
        return ResponseEntity.ok(userService.users(searchName, department));
    };

}
