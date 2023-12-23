package backend.backend.system.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException exception) {
        return ErrorResponseEntity.to(exception.getErrorCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponseEntity> handlerAccessDenied() {
        return ErrorResponseEntity.to(CustomErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponseEntity> handlerRuntimeException() {
        return ErrorResponseEntity.to(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponseEntity> handleSignatureException() {
        return ErrorResponseEntity.to(CustomErrorCode.INVALID_TOKEN_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ErrorResponseEntity> handleMalformedJwtException() {
        return ErrorResponseEntity.to(CustomErrorCode.INVALID_TOKEN_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponseEntity> handleExpiredJwtException() {
        return ErrorResponseEntity.to(CustomErrorCode.TOKEN_EXPIRED_ERROR);
    }

}
