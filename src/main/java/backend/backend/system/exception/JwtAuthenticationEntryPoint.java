package backend.backend.system.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Spring Security에서 인증과 관련된 예외가 발생했을 때 처리하는 로직
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    /**
     *
     * @param resolver
     *
     * HandlerExceptionResolver의 빈이 두 종류가 있기 때문에
     * @Qualifier로 handlerExceptionResolver를 주입받을 것이라고 명시
     */
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }


    /**
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     *
     * commence()에서 스프링 시큐리티의 인증 관련 예외를 처리하게 된다.
     * @RestControllerAdvice에서 모든 예외를 처리하여 응답할 것이기 때문에
     * 여기에 별다른 로직은 작성하지 않고 HandlerExceptionResolver에 예외 처리를 위임한다.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, authException);
    }
}
