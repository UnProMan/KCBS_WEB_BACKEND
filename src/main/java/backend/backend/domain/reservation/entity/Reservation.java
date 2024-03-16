package backend.backend.domain.reservation.entity;

import backend.backend.domain.inventory.entity.Inventory;
import backend.backend.domain.reservation_SD.entity.Reservation_SD;
import backend.backend.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "RESERVATION_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RESERVATION_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID", referencedColumnName = "ID")
    private Inventory inventory;

    @Column(name = "START_DT", nullable = false)
    private LocalDateTime start_DT;

    @Column(name = "RETURN_DT", nullable = false)
    private LocalDateTime return_DT;

    @Column(name = "TRIPOD_COUNT", nullable = false)
    @ColumnDefault("0")
    private Integer tripod_Count;

    @Column(name = "BREAK_REPORT", nullable = false)
    private String break_Report;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Reservation_SD> reservation_SDs;

}
