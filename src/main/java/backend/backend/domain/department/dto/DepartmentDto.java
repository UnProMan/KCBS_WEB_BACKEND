package backend.backend.domain.department.dto;

import backend.backend.domain.department.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DepartmentDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String name;

        public static Response from(Department department) {
            return Response.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .build();
        }
    }

}
