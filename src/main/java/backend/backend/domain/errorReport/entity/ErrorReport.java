package backend.backend.domain.errorReport.entity;

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
        name = "ERROR_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "ERROR_REPORT")
public class ErrorReport {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ERROR_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ErrorStates errorStates;

}
