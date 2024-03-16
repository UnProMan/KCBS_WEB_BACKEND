package backend.backend.domain.department.entity;

import backend.backend.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "PARENT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "PARENT_DEPARTMENT")
public class ParentDepartment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PARENT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARENT_NAME", nullable = false)
    private String parentName;

    @OneToMany(mappedBy = "parentDepartment")
    private List<Department> departments;

}
