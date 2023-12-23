package backend.backend.domain.equipment_type.entity;

import backend.backend.domain.inventory.entity.Inventory;
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
        name = "EQUIPMENT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Equipment_Type {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EQUIPMENT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_NAME")
    @NotNull
    private String type_Name;

    @OneToMany(mappedBy = "equipment_Type")
    private List<Inventory> inventories;

}
