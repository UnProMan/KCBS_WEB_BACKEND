package backend.backend.domain.department.entity;

import backend.backend.domain.parent_department.entity.Parent_Department;
import backend.backend.domain.team.entity.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "DEPARTMENT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Department {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DEPARTMENT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEPARTMENT_NAME")
    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private Parent_Department parent_Department;

    @OneToMany(mappedBy = "department")
    private List<Team> teams;

}
