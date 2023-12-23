package backend.backend.domain.join_user.entity;

import backend.backend.domain.join_user.dto.Join_UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class Join_User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "JOIN_SEQ_GENERATOR"
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
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phone_number;

    public static Join_User from(Join_UserDto.Response userDto, PasswordEncoder encoder) {
        return Join_User.builder()
                .id(userDto.getId())
                .studentId(userDto.getStudentId())
                .name(userDto.getName())
                .password(encoder.encode(userDto.getPassword()))
                .birthday(userDto.getBirthday())
                .phone_number(userDto.getPhone_number())
                .build();
    }

}
