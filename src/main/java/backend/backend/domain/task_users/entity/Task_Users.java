package backend.backend.domain.task_users.entity;

import backend.backend.domain.daily_task.entity.Daily_Task;
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
public class Task_Users {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TASK_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
    private Daily_Task daily_Task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;


}
