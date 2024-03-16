package backend.backend.system.security.jwt;

import backend.backend.system.security.refreshToken.entity.RefreshToken;
import backend.backend.system.security.refreshToken.repository.RefreshTokenRepository;
import backend.backend.domain.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@PropertySource("classpath:jwt.yml")
@Service
@Transactional(readOnly = true)
public class TokenProvider {
    private final String secretKey;
    private final long expirationMinutes;
    private final long refreshExpirationHours;
    private final String issuer;
    private final int reissueLimit;
    private final RefreshTokenRepository refreshTokenRepository;

    private final ObjectMapper objectMapper;

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            RefreshTokenRepository refreshTokenRepository,
            ObjectMapper objectMapper
    ) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
        this.refreshTokenRepository = refreshTokenRepository;
        this.reissueLimit = (int) (refreshExpirationHours * 60 / expirationMinutes) + 50;
        this.objectMapper = objectMapper;
    }

    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                // HS256 알고리즘을 사용하여 secretKey를 이용해 서명
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .setSubject(userSpecification) // JWT 토큰 제목
                .setIssuer(issuer) // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES))) // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    /**
     *
     * @param token
     * @return String
     *
     * validateAndParseToken() 호출하여 검증하고,
     * 토큰의 주체(SubJect)를 추출
     *
     */
    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token)
                .getBody()
                .getSubject();
    }

    /**
     *
     * @return String
     * @throws JsonProcessingException
     *
     * RefreshToken을 토대로 새로운 AccessToken 생성
     * RefreshRepository에서 cookie_Refresh_Token 토대로 재발급 한도에 초과하지 않은
     * RefreshToken을 찾고, 재발급 횟수 + 1 하고, 새로운 AccessToken을 발행한다.
     *
     */
    @Transactional
    public String recreateAccessToken(String cookie_refreshToken) throws JsonProcessingException {
        RefreshToken refreshToken = getRefreshToken(cookie_refreshToken)
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh Token expired"));
        User user = refreshToken.getUser();

        refreshToken.increaseReissueCount();

        return createAccessToken(String.format("%s:%s", user.getId(), user.getRole()));
    }

    /**
     *
     * @param refreshToken
     * @throws JsonProcessingException
     *
     * RefreshToken이 유요한지 검증
     * validateAndParseToken()을 호출하여 RefreshToken 자체가 유효한지 검사
     * oldAccessToken을 통해 테이블에서 로그인 기록을 찾고,
     * 찾은 로그인 기록의 refreshToken과 인자로 받은 refreshToken과 비교하여 유효성 검사
     *
     */

    public void validateRefreshToken(String oldAccessToken ,String refreshToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String id = decodeJwtSubject(oldAccessToken).split(":")[0];
        refreshTokenRepository.findByIdAndReissueCountLessThan(Long.parseLong(id), reissueLimit)
                .filter(f -> f.validRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh Token expired"));
    }

    /**
     *
     * @param token
     * @return Jws<Claims>
     *
     * 내부의  parseCliamJws()애서 JWT를 파싱할 때,
     * 토큰이 유효한지 검사하는 검증기능
     *
     */
    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    /**
     *
     * @param request
     * @return String
     *
     * request에서 AcessToken을 추출
     *
     */
    public String parseAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    /**
     *
     * @param oldAccessToken
     * @return
     * @throws JsonProcessingException
     *
     * oldAccessToken 디코딩 후 서브젝트 반환
     *
     */
    private String decodeJwtSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
            new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
            Map.class
        ).get("sub").toString();
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshTokenAndReissueCountLessThan(refreshToken, reissueLimit);
    }

}
