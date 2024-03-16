package backend.backend.domain.inventory.entity;

import backend.backend.domain.inventory.entity.Inventory;
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
        name = "EQUIPMENT_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "EQUIPMENT_TYPE")
public class EquipmentType {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EQUIPMENT_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_NAME", nullable = false)
    private String typeName;

    @OneToMany(mappedBy = "equipmentType")
    private List<Inventory> inventories;

}
