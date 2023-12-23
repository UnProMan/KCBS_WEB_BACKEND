package backend.backend.domain.user.entity;

import org.springframework.security.core.GrantedAuthority;

public enum ROLE implements GrantedAuthority {

    /*
     * 국장,
     * 부국장,
     * 일반유저,
     * 제작팀장,
     * 기술팀장,
     * 아나운서팀장,
     * PD팀장,
     * 촬영팀장,
     * 편집팀장,
     * 앵커팀장,
     * 관리자
     */

    ROLE_PRESIDENT,
    ROLE_VICE_PRESIDENT,
    ROLE_USER,
    ROLE_PRODUCTION_LEADER,
    ROLE_ENGINEER_LEADER,
    ROLE_ANNOUNCER_LEADER,
    ROLE_PRODUCER_LEADER,
    ROLE_CINEMA_LEADER,
    ROLE_EDITING_LEADER,
    ROLE_ANCHOR_LEADER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
