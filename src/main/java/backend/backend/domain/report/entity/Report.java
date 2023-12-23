package backend.backend.domain.report.entity;

import backend.backend.domain.daily_task.entity.Daily_Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "REPORT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Report {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "REPORT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "ID")
    private Daily_Task daily_Task;

    @Lob
    @Column(name = "CONTENT")
    private String content;

}
