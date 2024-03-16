package backend.backend.domain.dailyTask.dto;

import backend.backend.domain.dailyTask.entity.TaskUsers;
import backend.backend.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TaskUsersDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskUsersResponse {
        private Long id;
        private UserDto.Response user;

        public static TaskUsersResponse from(TaskUsers taskUsers) {
            return TaskUsersResponse.builder()
                    .id(taskUsers.getId())
                    .user(UserDto.Response.from(taskUsers.getUser()))
                    .build();
        }
    }

}
