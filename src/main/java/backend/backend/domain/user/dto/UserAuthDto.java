package backend.backend.domain.user.dto;

import backend.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 로그인 DTO
 */

public class UserAuthDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        @JsonProperty("studentId")
        private String studentId;
        private String password;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class LoginResponse extends UserDto.Response {
        private String accessToken;

        public static LoginResponse of(User user, String accessToken) {
            return LoginResponse.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .phoneNumber(user.getPhoneNumber())
                    .attendanceState(user.getAttendanceState())
                    .role(user.getRole())
                    .kisu(user.getKisu())
                    .fileID(user.getFileID())
                    .accessToken(accessToken)
                    .build();
        }
    }

}
