package backend.backend.system.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException exception) {
        return ErrorResponseEntity.to(exception.getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponseEntity> handlerRuntimeException() {
        return ErrorResponseEntity.to(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseEntity> handleSignatureException() {
        return ErrorResponseEntity.to(CustomErrorCode.INVALID_TOKEN_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseEntity> handleMalformedJwtException() {
        return ErrorResponseEntity.to(CustomErrorCode.INVALID_TOKEN_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseEntity> handleExpiredJwtException() {
        return ErrorResponseEntity.to(CustomErrorCode.TOKEN_EXPIRED_ERROR);
    }

}
