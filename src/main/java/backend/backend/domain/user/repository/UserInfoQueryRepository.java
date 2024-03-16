package backend.backend.domain.user.repository;

import static backend.backend.domain.user.entity.QUser.user;
import static backend.backend.domain.department.entity.QTeam.team;
import static backend.backend.domain.department.entity.QDepartment.department;

import backend.backend.domain.user.entity.AttendanceState;
import backend.backend.domain.user.entity.ROLE;
import backend.backend.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserInfoQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     *
     * @param name
     * @param department_id
     * @return 현재 재학이면서 명예국원이 아니고,  필터에 맞게 검색된 모든 KCBS 인원들
     */
    public List<User> findUsers(String name, Long department_id) {
        return jpaQueryFactory.selectFrom(user)
                .leftJoin(user.teams, team).fetchJoin()
                .leftJoin(team.department, department).fetchJoin()
                .where(
                        user.attendanceState.eq(AttendanceState.재학)
                                .and(user.role.notIn(ROLE.ROLE_HONOR))
                                .and(user.name.contains(name))
                                .and(eqDepartment(department_id))
                )
                .orderBy(user.kisu.asc(), user.name.asc())
                .fetch();
    }


    /**
     * department_id가 0인 경우 모든 부서 검색, 0이 아닌경우 해당 부서만 검색하는 조건문
     * @param department_id
     * @return
     */
    private BooleanExpression eqDepartment(Long department_id) {
        if (department_id == 0) return null;
        return department.id.eq(department_id);
    }

}
