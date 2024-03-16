package backend.backend.domain.joinUser.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class JoinUserDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class JoinUserRequest {

        @NotBlank
        @Size(min = 10, max = 10)
        private String studentId;

        @NotBlank
        @Size(min = 1, max = 20, message = "이름은 20자 이하로 작성해주세요.")
        private String name;

        @NotBlank
        @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[a-zA-Z]{2,}$")
        private String email;

        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$")
        private String password;

        @Past
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;

        @NotBlank
        @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$")
        private String phone_Number;

        @Positive
        private int kisu;

    }

}
