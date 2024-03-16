package backend.backend.domain.user.service;

import backend.backend.domain.user.dto.UserInfoDto;
import backend.backend.domain.user.repository.UserInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * KCBS 인원 현황 비즈니스 로직
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {
    private final UserInfoQueryRepository userInfoQueryRepository;

    /**
     *
     * @param searchName 검색할 이름
     * @param department_id 검색할 부서
     * @return
     */
    public List<UserInfoDto.UsersResponse> users(String searchName, Long department_id) {
        return userInfoQueryRepository.findUsers(searchName, department_id)
                .stream().map(UserInfoDto.UsersResponse::from)
                .collect(Collectors.toList());
    }

}
