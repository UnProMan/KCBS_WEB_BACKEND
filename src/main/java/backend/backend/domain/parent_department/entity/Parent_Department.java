package backend.backend.domain.parent_department.entity;

import backend.backend.domain.department.entity.Department;
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
        name = "PARENT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Parent_Department {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PARENT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "PARENT_NAME")
    @NotNull
    private String parent_Name;

    @OneToMany(mappedBy = "parent_Department")
    private List<Department> departments;

}
