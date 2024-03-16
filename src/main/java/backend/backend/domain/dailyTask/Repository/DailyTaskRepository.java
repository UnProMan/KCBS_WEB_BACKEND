package backend.backend.domain.dailyTask.Repository;

import backend.backend.domain.dailyTask.entity.DailyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyTaskRepository extends JpaRepository<DailyTask, Integer> {
}
