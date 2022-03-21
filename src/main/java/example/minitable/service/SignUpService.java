package example.minitable.service;

import example.minitable.domain.RandomRegister;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.SignUpStoreOwnerRequest;
import example.minitable.dto.UserDto;
import example.minitable.exception.AlreadyRegisteredUserException;
import example.minitable.repository.RandomRegisterRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final RandomRegisterRepository randomRegisterRepository;

    @Transactional
    public User signupCustomer(UserDto userDto) {

        checkAlreadyRegistered(userDto); // 이미 존재하는 유저면 Exception 발생

        // authority 설정
        userDto.setAuthority("ROLE_CUSTOMER");

        return signup(userDto);

    }

    @Transactional
    public User signupStoreOwner(SignUpStoreOwnerRequest signUpStoreOwnerRequest) {

        UserDto userDto = UserDto.from(signUpStoreOwnerRequest);

        checkAlreadyRegistered(userDto); // 이미 존재하는 유저면 Exception 발생

        // 1) authority 설정, 유저 등록
        userDto.setAuthority("ROLE_STORE_OWNER");
        User registeredUser =  signup(userDto);

        // 2) Random_Register Random No 등록 여부 확인하고 등록되지 않은 값이라면 다음단계
        int cnt = 0;
        int tmpNo = 0;
        for (cnt = 1; cnt <= 100; cnt++) {  // 임시로 100번만 시도함

            tmpNo = (int) (Math.random() * 10000);

            RandomRegister findRandomRegister = randomRegisterRepository.findByRandomNo(tmpNo);

            if (findRandomRegister==null) {
                break;
            }
        }

        if(cnt==100) {
            throw new IllegalStateException("Random No 생성시 최대 횟수 도달");
        }

        // 3) 유일한 값이면 Random_Register 등록
        RandomRegister randomRegister = new RandomRegister(tmpNo);
        randomRegisterRepository.save(randomRegister);

        // 4) Store, Restaurant 등록 필요 -> Join 전략 으로 Restaurant 만 등록
        Restaurant restaurant = Restaurant.from(registeredUser, signUpStoreOwnerRequest);

        String strRandomNo = String.format("%04d", tmpNo);
        restaurant.setRandomNo(strRandomNo);
        restaurantRepository.save(restaurant);

        // TODO : (추후) offday 입력받아서 등록 하는 서비스 로직 필요

        return registeredUser;
    }

    @Transactional
    public User signup(UserDto userDto) {

        // 패스워드 암호화
        String encryptPwd = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptPwd);

        return userRepository.save(User.from(userDto));
    }


    private void checkAlreadyRegistered(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new AlreadyRegisteredUserException();
        }
    }

}
