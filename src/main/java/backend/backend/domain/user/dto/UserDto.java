package backend.backend.domain.user.dto;

import backend.backend.domain.team.entity.Team;
import backend.backend.domain.user.entity.Attendance_State;
import backend.backend.domain.user.entity.ROLE;
import backend.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class UserDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDto {
        @JsonProperty("studentId")
        private String studentId;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class LoginResponseDto {
        private Long id;
        private String studentId;
        private String name;
        private String email;
        private LocalDate birthday;
        private String phone_Number;
        private Attendance_State attendance_state;
        private ROLE role;
        private String file_ID;
        private String accessToken;

        public static LoginResponseDto from(User user, String accessToken) {
            return LoginResponseDto.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .phone_Number(user.getPhone_Number())
                    .attendance_state(user.getAttendance_State())
                    .role(user.getRole())
                    .file_ID(user.getFile_ID())
                    .accessToken(accessToken)
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String studentId;
        private String name;
        private String password;
        private LocalDate birthday;
        private String phone_Number;
        private Attendance_State attendance_State;
        private ROLE role;
        private String file_ID;
        private List<Team> teams;

        public static Response from(User user) {
            return Response.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .password(user.getPassword())
                    .birthday(user.getBirthday())
                    .phone_Number(user.getPhone_Number())
                    .attendance_State(user.getAttendance_State())
                    .role(user.getRole())
                    .file_ID(user.getFile_ID())
                    .teams(user.getTeams())
                    .build();
        }
    }
}
