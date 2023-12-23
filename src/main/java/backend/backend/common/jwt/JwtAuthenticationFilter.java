package backend.backend.common.jwt;

import backend.backend.domain.user.dto.PrincipalUser;
import backend.backend.domain.user.repository.UserRepository;
import backend.backend.system.exception.CustomErrorCode;
import backend.backend.system.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;


    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     *
     * 토큰 검증하는 메서드
     *
     * 만약 AccessToken이 없을 경우 doFilter로 넘김
     * AccessToken에 기간이 만료된 경우 reissueAccessToken() 호출
     * AccessToken 기간 만료가 안됐고, 유효한 토큰인 경우 SecurityContextHolder에 사용자 정보를 넣어둠
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);

            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            PrincipalUser principalUser = parseUserSpecification(accessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(principalUser, accessToken, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            reissueAccessToken(request, response, e);
        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.");
        }
    }

    /**
     *
     * @param request
     * @param headerName
     * @return String
     *
     * request에서 토큰을 추출
     *
     */
    private String parseBearerToken(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    /**
     *
     * @param token
     * @return
     *
     *
     *
     */
    private PrincipalUser parseUserSpecification(String token) {
        String userId = tokenProvider.validateTokenAndGetSubject(token);

        return new PrincipalUser(
                userRepository.findById(Long.parseLong(userId))
                        .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND))
        );
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        try {
            String refreshToken = parseBearerToken(request, "Refresh-Token");

            if (refreshToken == null) throw new CustomException(CustomErrorCode.INVALID_TOKEN_ERROR);

            String oldAccessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);
            tokenProvider.validateRefreshToken(refreshToken, oldAccessToken);
            String newAccessToken = tokenProvider.recreateAccessToken(oldAccessToken);

            PrincipalUser principalUser = parseUserSpecification(newAccessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(principalUser, newAccessToken, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            response.setHeader("New-Access-Token", newAccessToken);
        } catch (Exception e) {
            // after fix
            throw e;
        }

    }

}
