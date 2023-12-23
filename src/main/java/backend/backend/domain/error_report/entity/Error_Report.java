package backend.backend.domain.error_report.entity;

import backend.backend.domain.user.entity.User;
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
        name = "ERROR_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Error_Report {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ERROR_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Error_State error_state;

}
