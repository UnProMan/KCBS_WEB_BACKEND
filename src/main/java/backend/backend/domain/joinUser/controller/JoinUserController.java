package backend.backend.domain.joinUser.controller;

import backend.backend.domain.joinUser.dto.JoinUserDto;
import backend.backend.domain.joinUser.service.JoinUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinUserController {
    private final JoinUserService joinUserService;

    @PostMapping("/join")
    public void createUser(@RequestBody @Valid JoinUserDto.JoinUserRequest joinUserRequest) {
        joinUserService.signUser(joinUserRequest);
    }

}
