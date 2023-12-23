package backend.backend.domain.join_user.controller;

import backend.backend.domain.join_user.dto.Join_UserDto;
import backend.backend.domain.join_user.service.Join_UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/join")
public class Join_UserController {
    private Join_UserService joinUserService;

    @GetMapping
    public ResponseEntity<Join_UserDto.Response> join(@RequestBody Join_UserDto.Response joinUser) {
        return ResponseEntity.ok(joinUserService.join(joinUser));
    }

}
