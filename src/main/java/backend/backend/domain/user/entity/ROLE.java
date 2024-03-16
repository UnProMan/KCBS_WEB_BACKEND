package backend.backend.domain.user.entity;

import org.springframework.security.core.GrantedAuthority;

public enum ROLE implements GrantedAuthority {

    /**
     * 국장
     */
    ROLE_PRESIDENT,

    /**
     * 부국장
     */
    ROLE_VICE_PRESIDENT,

    /**
     * 일반유저
     */
    ROLE_USER,

    /**
     * 제작팀장
     */
    ROLE_PRODUCTION_LEADER,

    /**
     * 기술팀장
     */
    ROLE_ENGINEER_LEADER,

    /**
     * 아나운서팀장
     */
    ROLE_ANNOUNCER_LEADER,

    /**
     * PD팀장
     */
    ROLE_PRODUCER_LEADER,

    /**
     * 촬영팀장
     */
    ROLE_CINEMA_LEADER,

    /**
     * 편집팀장
     */
    ROLE_EDITING_LEADER,

    /**
     * 앵커팀장
     */
    ROLE_ANCHOR_LEADER,

    /**
     * 관리자
     */
    ROLE_ADMIN,

    /**
     * 신입국원
     */
    ROLE_NEWCOMER,

    /**
     * 명예국원
     */
    ROLE_HONOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
