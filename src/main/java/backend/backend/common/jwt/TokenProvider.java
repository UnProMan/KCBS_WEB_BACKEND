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
    private final long reissueLimit;
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
        this.reissueLimit = refreshExpirationHours * 60 / expirationMinutes;
    }

    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification) // JWT 토큰 제목
                .setIssuer(issuer) // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES))) // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        return validateAndParseToken(token)
                .getBody()
                .getSubject();
    }

    @Transactional
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        refreshTokenRepository.findByIdAndReissueCountLessThan(Long.valueOf(subject.split(":")[0]), reissueLimit)
                .ifPresentOrElse(
                        RefreshToken::increaseReissueCount,
                        () -> { new ExpiredJwtException(null, null, "Refresh Token expired"); }
                );
        return createAccessToken(subject);
    }

    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String userId = decodeJwtPayloadSubject(oldAccessToken).split(":")[0];
        refreshTokenRepository.findByIdAndReissueCountLessThan(Long.parseLong(userId), reissueLimit)
                .filter(userRefreshToken -> userRefreshToken.validateRefreshToken(refreshToken))
                .orElseThrow(() -> new ExpiredJwtException(null, null, "Refresh Token expired"));
    }

    public Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token);
    }

    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }

}
