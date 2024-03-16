package backend.backend.domain.joinUser.repository;

import backend.backend.domain.joinUser.entity.JoinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinUserRepository extends JpaRepository<JoinUser, Integer> {
    boolean existsByStudentId(String studentId);
}
