package backend.backend.system.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private int httpStatus;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> to(CustomErrorCode error) {
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(
                        ErrorResponseEntity.builder()
                                .httpStatus(error.getHttpStatus().value())
                                .code(error.name())
                                .message(error.getMessage())
                                .build()
                );
    }
}
