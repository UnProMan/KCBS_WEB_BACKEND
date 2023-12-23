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

    /**
     *
     * @param loginRequestDto
     * @return TokenDto
     *
     * 로그인 화면에서 학번과 비밀번호를 입력받고,
     * 학번과 비밀번호를 입력 받으면 DB에서 해당 정보를 찾고 없으면 에러
     *
     * 해당 정보의 사람을 찾으면 AccessToken과 RefreshToken을 생성
     * 만약 로그인 한 유저가 이미 RefreshToken이 존재하면 새로 생성한 RefreshToken으로 변경
     * RefreshToken이 존재하지 않으면 새로 추가
     *
     */
    @Transactional
    public TokenDto login(UserDto.LoginRequestDto loginRequestDto) {
        User user = userRepository.findByStudentId(loginRequestDto.getStudentId())
                .filter(T -> encoder.matches(loginRequestDto.getPassword(), T.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        String accessToken = tokenProvider.createAccessToken(String.valueOf(user.getId()));
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
