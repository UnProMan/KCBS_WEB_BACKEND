package backend.backend.domain.user.entity;

import backend.backend.domain.team.entity.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class User {
    // h2 DB 사용할때만 시퀀스 사용 추후에 시퀀스 지우기
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USER_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "STUDENT_ID", unique = true)
    private String studentId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name="EMAIL")
    private String email;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phone_Number;

    @NotNull
    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private Attendance_State attendance_State;

    @NotNull
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(name = "FILE_ID")
    private String file_ID;

    @OneToMany(mappedBy = "user")
    private List<Team> teams;

    public User(Long id, ROLE role) {
        this.id = id;
        this.role = role;
    }

}
