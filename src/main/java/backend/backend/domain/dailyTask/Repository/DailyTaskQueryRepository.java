package backend.backend.domain.dailyTask.Repository;

import backend.backend.domain.dailyTask.dto.DailyTaskDto;
import backend.backend.domain.dailyTask.entity.DailyTask;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static backend.backend.domain.dailyTask.entity.QDailyTask.dailyTask;
import static backend.backend.domain.taskUsers.entity.QTaskUsers.taskUsers;
import static backend.backend.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class DailyTaskQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<DailyTask> findAllDailyTasks(LocalDate date) {
        return jpaQueryFactory.selectFrom(dailyTask)
                .leftJoin(dailyTask.users, taskUsers).fetchJoin()
                .leftJoin(taskUsers.user, user).fetchJoin()
                .where(
                        dailyTask.date
                                .between(date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()))
                )
                .orderBy(dailyTask.date.asc(), dailyTask.id.asc())
                .fetch();
    }

}
