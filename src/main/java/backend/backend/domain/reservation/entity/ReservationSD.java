package backend.backend.domain.reservation.entity;

import backend.backend.domain.inventory.entity.Inventory;
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
public class ReservationSD {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SD_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "ID")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID", referencedColumnName = "ID")
    private Inventory inventory;

}
