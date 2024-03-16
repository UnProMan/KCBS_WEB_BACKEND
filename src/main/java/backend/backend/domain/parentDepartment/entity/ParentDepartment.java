package backend.backend.domain.parentDepartment.entity;

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
    private Integer id;

    @Column(name = "PARENT_NAME", nullable = false)
    private String parent_Name;

    @OneToMany(mappedBy = "parentDepartment")
    private List<Department> departments;

}
