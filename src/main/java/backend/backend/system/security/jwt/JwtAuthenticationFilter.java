package backend.backend.system.security.jwt;

import backend.backend.common.utils.CookieUtils;
import backend.backend.domain.user.dto.PrincipalUser;
import backend.backend.domain.user.entity.ROLE;
import backend.backend.system.exception.ErrorCode;
import backend.backend.system.exception.RestException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

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
     * 만약 AccessToken && RefreshToken 없을 경우 doFilter로 넘김
     * AccessToken에 기간이 만료된 경우 reissueAccessToken() 호출
     * AccessToken 기간 만료가 안됐고, 유효한 토큰인 경우 SecurityContextHolder에 사용자 정보를 넣어둠
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = tokenProvider.parseAccessToken(request);

            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            PrincipalUser principalUser = parseUserSpecification(accessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(principalUser, accessToken, principalUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticated);

        } catch (ExpiredJwtException e) {
            reissueAccessToken(request, response, e);
        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "토큰이 유효하지 않습니다.");
        }

        filterChain.doFilter(request, response);
    }

    /**
     *
     * @param token
     * @return PrincipalUser
     *
     * AccessToken을 받고, 받은 정보를 통해 로그인한 유저 정보를 가져옴
     *
     */
    private PrincipalUser parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .map(tokenProvider::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new PrincipalUser(
                Long.parseLong(split[0]),
                ROLE.valueOf(split[1])
        );
    }

    /**
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     *
     * RefreshToken의 유효성 검사, oldAccessToken의 유효성 검사 후
     * 유효한 RefreshToken의 경우 새로운 AccessToken 생성
     *
     */
    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        try {
            String refreshToken = CookieUtils.findCookie("KCBS-Refresh-Token", request);

            if (refreshToken == null) throw new RestException(ErrorCode.GLOBAL_INVALID_TOKEN_ERROR);

            String oldAccessToken = tokenProvider.parseAccessToken(request);

            tokenProvider.validateRefreshToken(oldAccessToken, refreshToken);
            String newAccessToken = tokenProvider.recreateAccessToken(refreshToken);

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
