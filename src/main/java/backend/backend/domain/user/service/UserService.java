package backend.backend.domain.user.service;

import backend.backend.common.jwt.TokenDto;
import backend.backend.common.jwt.TokenProvider;
import backend.backend.common.refreshToken.entity.RefreshToken;
import backend.backend.common.refreshToken.repository.RefreshTokenRepository;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.entity.User;
import backend.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto login(UserDto.LoginDto loginDto) {
        User user = userRepository.findByStudentId(loginDto.getStudentId())
                .filter(T -> encoder.matches(loginDto.getPassword(), T.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", user.getId(), user.getRole()));
        String refreshToken = tokenProvider.createRefreshToken();

        // 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
        refreshTokenRepository.findById(user.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(user, refreshToken))
                );

        return new TokenDto(user.getName(), user.getRole(), accessToken, refreshToken);
    }
}
