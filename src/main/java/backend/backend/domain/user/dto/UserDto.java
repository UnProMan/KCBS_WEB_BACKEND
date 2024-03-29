package backend.backend.domain.user.dto;

import backend.backend.domain.department.dto.DepartmentDto;
import backend.backend.domain.user.entity.AttendanceState;
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

/**
 * User 공통 DTO
 */

public class UserDto {

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class Response {
        private Long id;
        private String studentId;
        private String name;
        private String email;
        private LocalDate birthday;
        private String phoneNumber;
        private AttendanceState attendanceState;
        private ROLE role;
        private Integer kisu;
        private String fileID;

        public static Response from(User user) {
            return Response.builder()
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
                    .build();
        }
    }

}
