package backend.backend.domain.report.entity;

import backend.backend.domain.dailyTask.entity.DailyTask;
import jakarta.persistence.*;
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
@Table(name = "REPORT")
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
    private DailyTask dailyTask;

    @Lob
    @Column(name = "CONTENT")
    private String content;

}
