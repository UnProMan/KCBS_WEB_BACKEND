package backend.backend.domain.user.entity;

import backend.backend.domain.department.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "USERS")
@DynamicInsert
public class User {
    // h2 DB 사용할때만 시퀀스 사용 추후에 시퀀스 지우기
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

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

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceState attendanceState;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(name = "KISU", nullable = false)
    private Integer kisu;

    @ColumnDefault("'1t_yV0Me_cx93jmjZOOVfqvzUiMmg9-r5'")
    @Column(name = "FILE_ID")
    private String fileID;

    @OneToMany(mappedBy = "user")
    private List<Team> teams;

    public User(Long id, ROLE role) {
        this.id = id;
        this.role = role;
    }

}
