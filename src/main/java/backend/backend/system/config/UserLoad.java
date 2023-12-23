package backend.backend.system.config;

import backend.backend.domain.user.entity.Attendance_State;
import backend.backend.domain.user.entity.ROLE;
import backend.backend.domain.user.entity.User;
import backend.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserLoad {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Bean
    public void userInsert() {

        User user = User.builder()
                .id(1L)
                .studentId("2022136131")
                .name("허명철")
                .email("soon3771@koreatech.ac.kr")
                .password(encoder.encode("1234"))
                .birthday(LocalDate.of(2003, 9, 11))
                .phone_Number("010-2699-3771")
                .attendance_State(Attendance_State.재학)
                .role(ROLE.ROLE_ADMIN)
                .file_ID("")
                .build();

        userRepository.save(user);
    }

}
