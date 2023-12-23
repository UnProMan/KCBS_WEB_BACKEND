package backend.backend.domain.daily_task.entity;

import backend.backend.domain.task_users.entity.Task_Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Daily_Task {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DAILY_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE")
    @NotNull
    private LocalDate date;

    @NotNull
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Task_Type task_Type;

    @OneToMany(mappedBy = "daily_Task", cascade = CascadeType.ALL)
    private List<Task_Users> users;

}
