package backend.backend.common.jwt;

import backend.backend.domain.user.entity.ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    private final String name;
    private final ROLE role;
    private final String accessToken;
    private final String refreshToken;
}
