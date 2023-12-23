package backend.backend.domain.join_user.repository;

import backend.backend.domain.join_user.entity.Join_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Join_UserRepository extends JpaRepository<Join_User, Long> {
}
