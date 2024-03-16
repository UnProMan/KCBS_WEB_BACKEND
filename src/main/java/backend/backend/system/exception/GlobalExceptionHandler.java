package backend.backend.system.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 커스텀 에러 처리
     */
    @ExceptionHandler(RestException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(RestException exception) {
        return ErrorResponseEntity.to(exception.getErrorCode());
    }

    /**
     * @Valid 검증이 실패했을 경우
     *
     * 만약 @Valid 검증 후 따로 message가 있을 경우 그 message로 응답
     * 없으면 공용 message로 응답
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseEntity> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if (message != null)  {
            ErrorCode errorCode = ErrorCode.GLOBAL_NOT_VALID_DATA;
            errorCode.setMessage(message);

            return ErrorResponseEntity.to(errorCode);
        }

        return ErrorResponseEntity.to(ErrorCode.GLOBAL_NOT_VALID_DATA);
    }

    /**
     * Request 요청에 Json Body가 잘못됐을 경우
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponseEntity> handleHttpMessageNotReadableException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_NOT_VALID_JSON_BODY);
    }

    /**
     * 특정 액세스 또는 기능에 대한 엑세스 기능이 없을 때
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponseEntity> handleAccessDenied() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_ACCESS_DENIED);
    }

    /**
     * 잘못된 인증 시도 시
     */
    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ErrorResponseEntity> handleSignatureException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_INVALID_TOKEN_ERROR);
    }

    /**
     * JWT 형식이 유요하지 않을 때 발생
     */
    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ErrorResponseEntity> handleMalformedJwtException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_INVALID_TOKEN_ERROR);
    }

    /**
     * 토큰 유효시간이 지났을 때 발생
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponseEntity> handleExpiredJwtException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_TOKEN_EXPIRED_ERROR);
    }

    /**
     * 파일을 찾을 수 없을 때 발생
     */
    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<ErrorResponseEntity> handleFileNotFoundException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_NOT_FOUND_FILE);
    }

    /**
     * 파일 입출력 작업 중 에러 발생 시
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ErrorResponseEntity> handleIOException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_FILE_ACCESS_ERROR);
    }

    /**
     * 보안 작업 중 에러 발생 시
     */
    @ExceptionHandler(GeneralSecurityException.class)
    protected ResponseEntity<ErrorResponseEntity> handleGeneralSecurityException() {
        return ErrorResponseEntity.to(ErrorCode.GLOBAL_GENERAL_SECURITY_ERROR);
    }

    /**
     * 그 외의 오류 발생 시
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponseEntity> handleRuntimeException(RuntimeException e) {

        System.out.println("-----------------------------------------");
        e.printStackTrace();
        System.out.println("-----------------------------------------");

        return ErrorResponseEntity.to(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
