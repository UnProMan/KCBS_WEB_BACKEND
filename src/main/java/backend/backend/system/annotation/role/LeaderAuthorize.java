package backend.backend.system.annotation.role;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(
        "hasAuthority('ROLE_PRODUCTION_LEADER')" +
        " or hasAuthority('ROLE_ENGINEER_LEADER')" +
        " or hasAuthority('ROLE_ANNOUNCER_LEADER')" +
        " or hasAuthority('ROLE_PRODUCER_LEADER')" +
        " or hasAuthority('ROLE_CINEMA_LEADER')" +
        " or hasAuthority('ROLE_EDITING_LEADER')" +
        " or hasAuthority('ROLE_ANCHOR_LEADER')"
)
public @interface LeaderAuthorize {
}
