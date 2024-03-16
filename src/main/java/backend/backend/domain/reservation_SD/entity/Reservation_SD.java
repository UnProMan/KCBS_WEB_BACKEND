package backend.backend.domain.reservation_SD.entity;

import backend.backend.domain.inventory.entity.Inventory;
import backend.backend.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "SD_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "RESERVATION_SD")
public class Reservation_SD {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SD_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "ID")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID", referencedColumnName = "ID")
    private Inventory inventory;

}
