package backend.backend.domain.dailyTask.entity;

import backend.backend.domain.taskUsers.entity.TaskUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "DAILY_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "DAILY_TASK")
public class DailyTask {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DAILY_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskTypes taskTypes;

    @OneToMany(mappedBy = "dailyTask", cascade = CascadeType.ALL)
    private List<TaskUsers> users;

}
