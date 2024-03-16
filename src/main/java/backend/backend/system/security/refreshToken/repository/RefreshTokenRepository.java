package backend.backend.system.security.refreshToken.repository;

import backend.backend.system.security.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    /**
     *
     * @param refreshToken
     * @param count
     * @return Optional<RefreshToken>
     *
     * 발급 제한 횟수보다 적으면서 해당 인자로 받은 RefreshToken과 같은 정보를 가져옴
     *
     */
    Optional<RefreshToken> findByRefreshTokenAndReissueCountLessThan(String refreshToken, int count);
    Optional<RefreshToken> findByIdAndReissueCountLessThan(Integer id, int count);

}
