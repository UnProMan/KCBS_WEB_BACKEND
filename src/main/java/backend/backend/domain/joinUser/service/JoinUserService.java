package backend.backend.domain.joinUser.service;

import backend.backend.domain.joinUser.dto.JoinUserDto;
import backend.backend.domain.joinUser.entity.JoinUser;
import backend.backend.domain.joinUser.repository.JoinUserRepository;
import backend.backend.domain.user.repository.UserRepository;
import backend.backend.system.exception.ErrorCode;
import backend.backend.system.exception.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinUserService {
    private final JoinUserRepository joinUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void signUser(JoinUserDto.JoinUserRequest joinUserRequest) {
        // 이미 user 테이블에 존재하는 학번일 경우 Exception
       if (userRepository.existsByStudentId(joinUserRequest.getStudentId()))
           throw new RestException(ErrorCode.USER_ALREADY_EXISTS);

       // 이미 join_user 테이블에 존재하는 학번일 경우 Exception
       if (joinUserRepository.existsByStudentId(joinUserRequest.getStudentId()))
           throw new RestException(ErrorCode.JOIN_USER_ALREADY_EXISTS);

       joinUserRepository.save(
                JoinUser.builder()
                        .studentId(joinUserRequest.getStudentId())
                        .name(joinUserRequest.getName())
                        .email(joinUserRequest.getEmail())
                        .password(encoder.encode(joinUserRequest.getPassword()))
                        .birthday(joinUserRequest.getBirthday())
                        .phoneNumber(joinUserRequest.getPhoneNumber())
                        .kisu(joinUserRequest.getKisu())
                        .build()
       );
    }

}
