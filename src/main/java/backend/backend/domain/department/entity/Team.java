package backend.backend.domain.department.entity;

import backend.backend.domain.department.entity.Department;
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
        name = "TEAM_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "TEAM")
public class Team {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TEAM_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID")
    private Department department;

}
