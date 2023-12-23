package backend.backend.system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
