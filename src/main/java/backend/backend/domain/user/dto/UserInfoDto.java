package backend.backend.domain.user.dto;

import backend.backend.domain.department.dto.DepartmentDto;
import backend.backend.domain.user.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * KCBS 인원현황 DTO
 */

public class UserInfoDto {

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class UsersResponse extends UserDto.Response {
        private List<DepartmentDto.Response> departments;

        public static UsersResponse from(User user) {
            return UsersResponse.builder()
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
                    .departments(
                            user.getTeams().stream()
                                    .map(team -> DepartmentDto.Response.from(team.getDepartment()))
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

}
