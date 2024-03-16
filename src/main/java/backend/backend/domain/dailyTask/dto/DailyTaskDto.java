package backend.backend.domain.dailyTask.dto;

import backend.backend.domain.dailyTask.entity.DailyTask;
import backend.backend.domain.dailyTask.entity.TaskTypes;
import backend.backend.domain.taskUsers.dto.TaskUsersDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DailyTaskDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class DailyTasksResponse {
        private Integer id;
        private LocalDate date;
        private TaskTypes taskTypes;
        private List<TaskUsersDto.TaskUsersResponse> taskUsers;

        public static DailyTasksResponse from(DailyTask dailyTask) {
            return DailyTasksResponse.builder()
                    .id(dailyTask.getId())
                    .date(dailyTask.getDate())
                    .taskTypes(dailyTask.getTaskTypes())
                    .taskUsers(
                            dailyTask.getUsers()
                                    .stream().map(TaskUsersDto.TaskUsersResponse::from)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

}
