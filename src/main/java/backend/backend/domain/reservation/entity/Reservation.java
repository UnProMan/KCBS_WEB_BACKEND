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
public class Reservation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RESERVATION_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ID", referencedColumnName = "ID")
    private Inventory inventory;

    @NotNull
    @Column(name = "START_DT")
    private LocalDateTime start_DT;

    @NotNull
    @Column(name = "RETURN_DT")
    private LocalDateTime return_DT;

    @NotNull
    @Column(name = "TRIPOD_COUNT")
    @ColumnDefault("0")
    private Long tripod_Count;

    @Column(name = "BREAK_REPORT")
    private String break_Report;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Reservation_SD> reservation_SDs;

}
