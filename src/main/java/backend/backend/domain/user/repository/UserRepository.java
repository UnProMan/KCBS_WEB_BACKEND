package backend.backend.domain.user.repository;

import backend.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByStudentId(String studentId);
    boolean existsByStudentId(String studentId);
}
