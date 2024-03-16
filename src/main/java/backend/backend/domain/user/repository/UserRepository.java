package backend.backend.domain.user.repository;

import backend.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 학번으로 유저 찾기
     * @param studentId 학번
     * @return
     */
    Optional<User> findByStudentId(String studentId);

    /**
     * 해당 학번이 유저 테이블에 있는지 체크
     * @param studentId 학번
     * @return
     */
    boolean existsByStudentId(String studentId);
}
