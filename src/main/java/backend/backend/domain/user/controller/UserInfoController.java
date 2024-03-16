package backend.backend.domain.user.controller;


import backend.backend.common.utils.DriveUtils;
import backend.backend.domain.user.dto.UserInfoDto;
import backend.backend.domain.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * KCBS 인원 현황 API
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

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
    public ResponseEntity<List<UserInfoDto.UsersResponse>> getUsers(@RequestParam String searchName,
                                                                    @RequestParam(required = false) Long department) {
        return ResponseEntity.ok(userInfoService.users(searchName, department));
    };


}
