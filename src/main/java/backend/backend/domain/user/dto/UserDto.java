package backend.backend.domain.user.dto;

import backend.backend.domain.department.dto.DepartmentDto;
import backend.backend.domain.department.entity.Department;
import backend.backend.domain.team.entity.Team;
import backend.backend.domain.user.entity.Attendance_State;
import backend.backend.domain.user.entity.ROLE;
import backend.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

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
    public static class LoginResponse extends Response {
        private String accessToken;

        public static LoginResponse of(User user, String accessToken) {
            return LoginResponse.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .phone_Number(user.getPhone_Number())
                    .attendance_State(user.getAttendance_State())
                    .role(user.getRole())
                    .kisu(user.getKisu())
                    .file_ID(user.getFile_ID())
                    .accessToken(accessToken)
                    .build();
        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class UsersResponse extends Response {
        private List<DepartmentDto.Response> departments;

        public static UsersResponse from(User user) {
            return UsersResponse.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .phone_Number(user.getPhone_Number())
                    .attendance_State(user.getAttendance_State())
                    .role(user.getRole())
                    .kisu(user.getKisu())
                    .file_ID(user.getFile_ID())
                    .departments(
                            user.getTeams().stream()
                                    .map(team -> DepartmentDto.Response.from(team.getDepartment()))
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class Response {
        private Integer id;
        private String studentId;
        private String name;
        private String email;
        private LocalDate birthday;
        private String phone_Number;
        private Attendance_State attendance_State;
        private ROLE role;
        private Integer kisu;
        private String file_ID;

        public static Response from(User user) {
            return Response.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .phone_Number(user.getPhone_Number())
                    .attendance_State(user.getAttendance_State())
                    .role(user.getRole())
                    .kisu(user.getKisu())
                    .file_ID(user.getFile_ID())
                    .build();
        }
    }

}
