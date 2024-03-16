package backend.backend.domain.taskUsers.entity;

import backend.backend.domain.dailyTask.entity.DailyTask;
import backend.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "TASK_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "TASK_USERS")
public class TaskUsers {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TASK_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
    private DailyTask dailyTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;


}
