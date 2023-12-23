package backend.backend.domain.inventory.entity;

import backend.backend.domain.equipment_type.entity.Equipment_Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "INVENTORY_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
public class Inventory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "INVENTORY_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "INVENTORY_NAME")
    @NotNull
    private String inventory_Name;

    /**
     * h2 DB는 TINYINT(1)를 지원 안하기때문에 일단 true, false형식으로 받을 수 있게 변환
     * 추후에 MySQL로 변경시 다시 변경
     */
    @Column(name = "AVAILABILITY")
//    @Column(name = "AVAILABILITY", columnDefinition = "TINYINT(1)")
//    @ColumnDefault("false")
    private boolean availability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID")
    private Equipment_Type equipment_Type;

}
