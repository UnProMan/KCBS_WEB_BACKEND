package backend.backend.domain.user.service;

import backend.backend.domain.user.repository.UserQueryRepository;
import backend.backend.system.security.jwt.TokenProvider;
import backend.backend.system.security.refreshToken.entity.RefreshToken;
import backend.backend.system.security.refreshToken.repository.RefreshTokenRepository;
import backend.backend.system.security.util.CookieUtils;
import backend.backend.domain.user.dto.PrincipalUser;
import backend.backend.domain.user.dto.UserDto;
import backend.backend.domain.user.entity.User;
import backend.backend.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserQueryRepository userQueryRepository;

    /**
     *
     * @param LoginRequest
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
    public UserDto.LoginResponse login(UserDto.LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByStudentId(loginRequest.getStudentId())
                .filter(T -> encoder.matches(loginRequest.getPassword(), T.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        String accessToken = tokenProvider.createAccessToken(String.format("%S:%s", user.getId(), user.getRole()));
        String refreshToken = tokenProvider.createRefreshToken();

        // 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
        refreshTokenRepository.findById(user.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(user, refreshToken))
                );

        response.addHeader("Set-Cookie", CookieUtils.createCookie("KCBS-Refresh-Token", refreshToken));

        return UserDto.LoginResponse.of(user, accessToken);
    }

    /**
     *
     * @param request
     * @param response
     *
     * 로그아웃하는 메서드
     * SecurityContextHolder 초기화 및 RefreshToken Table에 있는 유저 정보 삭제
     *
     */
    @Transactional
    public void logout(HttpServletRequest request ,HttpServletResponse response) {
        PrincipalUser user = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        refreshTokenRepository.deleteById(user.getUser().getId());
        CookieUtils.removeCookie("KCBS-Refresh-Token", request, response);
        SecurityContextHolder.clearContext();
    }

    /**
     *
     * @param request
     * @param response
     * @return UserDto.LoginResponse
     *
     * 클라이언트에서 새로고침 시
     * 쿠키에 RefreshToken이 없으면 로그아웃으로 간주
     * 쿠키에 RefreshToken이 있지만 RefreshToken Table에 쿠키에 있는 RefreshToken이 없으면 로그아웃으로 간주, 쿠키 삭제
     * 만약 전부 있으면 store에 다시 유저정보와 AccessToken을 넣어줌
     *
     */
    @Transactional
    public UserDto.LoginResponse refreshData(HttpServletRequest request, HttpServletResponse response) {
        try {

            String cookie_refreshToken = CookieUtils.findCookie("KCBS-Refresh-Token", request);
            if (cookie_refreshToken == null) return null;

            Optional<RefreshToken> refreshToken = tokenProvider.getRefreshToken(cookie_refreshToken);
            if (refreshToken.isEmpty()) {
                CookieUtils.removeCookie("KCBS-Refresh-Token", request, response);
                return null;
            }

            User user = refreshToken.get().getUser();
            String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", user.getId(), user.getRole()));

            refreshToken.get().increaseReissueCount();

            return UserDto.LoginResponse.of(user, accessToken);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param searchName 검색할 이름
     * @param department_id 검색할 부서
     * @return
     */
    public List<UserDto.UsersResponse> users(String searchName, Integer department_id) {
        return userQueryRepository.findUsers(searchName, department_id)
                .stream().map(UserDto.UsersResponse::from)
                .collect(Collectors.toList());
    }

}
