package backend.backend.system.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ErrorResponseEntity {
    private int httpStatus;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> to(ErrorCode error) {
        return ResponseEntity
                .status(error.getStatus())
                .body(
                        ErrorResponseEntity.builder()
                                .httpStatus(error.getStatus())
                                .code(error.name())
                                .message(error.getMessage())
                                .build()
                );
    }
}
