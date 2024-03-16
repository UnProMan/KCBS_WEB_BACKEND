package backend.backend.system.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;

public class CookieUtils {

    public static String createCookie(String name, String value) {
        return ResponseCookie.from(name, value)
//                .domain("localhost")
                .path("/")
                .maxAge(24 * 60 * 60)
//                .secure(true)
//                .httpOnly(true)
//                .sameSite("strict")
                .build()
                .toString();
    }

    public static void removeCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        Arrays.stream(request.getCookies())
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .ifPresent(cookie -> {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                });
    }

    public static String findCookie(String name, HttpServletRequest request) {
        try {
            return Arrays.stream(request.getCookies())
                    .filter(f -> f.getName().equals(name))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        } catch (Exception e) {
            // request.getCookies() <- Null then
            return null;
        }
    }

}
