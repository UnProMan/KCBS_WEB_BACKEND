package backend.backend.domain.join_user.dto;

import backend.backend.domain.join_user.entity.Join_User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class Join_UserDto {

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
        private String phone_number;

        public static Response from(Join_User user) {
            return Response.builder()
                    .id(user.getId())
                    .studentId(user.getStudentId())
                    .name(user.getName())
                    .password(user.getPassword())
                    .birthday(user.getBirthday())
                    .phone_number(user.getPhone_number())
                    .build();
        }
    }

}
