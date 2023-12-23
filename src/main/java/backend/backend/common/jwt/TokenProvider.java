package backend.backend.common.jwt;

import backend.backend.common.refreshToken.entity.RefreshToken;
import backend.backend.common.refreshToken.repository.RefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-hours}") long refreshExpirationHours,
            @Value("${issuer}") String issuer,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes;
        this.refreshExpirationHours = refreshExpirationHours;
        this.issuer = issuer;
        this.refreshTokenRepository = refreshTokenRepository;
        this.reissueLimit = (int) (refreshExpirationHours * 60 / expirationMinutes);
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
     * @param oldAccessToken
     * @return String
     * @throws JsonProcessingException
     *
     * 기존 AccessToken을 토대로 새로운 AccessToken 생성
     * RefreshRepository에서 기존 AccessToken에 담긴 정보를 토대로 재발급 한도에 초과하지 않은
     * RefreshToken을 찾고, 재발급 횟수 + 1 하고, 새로운 AccessToken을 발행한다.
     *
     */
    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        refreshTokenRepository.findByIdAndReissueCountLessThan(Long.parseLong(subject), reissueLimit)
                .ifPresentOrElse(
                        RefreshToken::increaseReissueCount,
                        () -> {
                            throw new ExpiredJwtException(null, null, "Refresh Token expired");
                        }
                );
        return createAccessToken(subject);
    }

    /**
     *
     * @param refreshToken
     * @param oldAccessToken
     * @throws JsonProcessingException
     *
     * RefreshToken이 유요한지 검증
     * validateAndParseToken()을 호출하여 RefreshToken 자체가 유효한지 검사
     * 만약 유효한 RefreshToken인 경우 oldAccessToken에 담긴 정보로 RefreshToken을 찾고,
     * 인자로 받은 RefreshToken과 찾은 RefreshToken 값을 비교하여 검증
     *
     */
    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String userId = decodeJwtPayloadSubject(oldAccessToken);
        refreshTokenRepository.findByIdAndReissueCountLessThan(Long.parseLong(userId), reissueLimit)
                .filter(userRefreshToken -> userRefreshToken.validRefreshToken(refreshToken))
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
    public Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    /**
     *
     * @param oldAccessToken
     * @return String
     * @throws JsonProcessingException
     *
     * JWT를 복호화 하고, 데이터가 담긴 Payload에서 SubJect 반환
     *
     */
    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }

}
