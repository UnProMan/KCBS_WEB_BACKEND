package backend.backend.domain.joinUser.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "JOIN_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "JOIN_USER")
public class JoinUser {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "JOIN_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "STUDENT_ID", unique = true, nullable = false)
    private String studentId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDate birthday;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "KISU", nullable = false)
    private Integer kisu;

}
