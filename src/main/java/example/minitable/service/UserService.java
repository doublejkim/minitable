package example.minitable.service;

import example.minitable.domain.User;
import example.minitable.dto.UserDto;
import example.minitable.exception.AlreadyRegisteredUserException;
import example.minitable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signupCustomer(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new AlreadyRegisteredUserException();
        }

        // authority 설정
        userDto.setAuthority("ROLE_CUSTOMER");

        // 패스워드 암호화
        String encryptPwd = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptPwd);


        return userRepository.save(User.from(userDto));

    }

}
