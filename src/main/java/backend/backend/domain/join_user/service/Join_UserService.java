package backend.backend.domain.join_user.service;

import backend.backend.domain.join_user.dto.Join_UserDto;
import backend.backend.domain.join_user.entity.Join_User;
import backend.backend.domain.join_user.repository.Join_UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Join_UserService {
    private final Join_UserRepository joinUserRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Join_UserDto.Response join(Join_UserDto.Response joinUser) {
        Join_User join_user = joinUserRepository.save(Join_User.from(joinUser, encoder));
        try {
            joinUserRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용중인 학번입니다.");
        }

        return Join_UserDto.Response.from(join_user);
    }

}
