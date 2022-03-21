package example.minitable.service;

import example.minitable.domain.User;
import example.minitable.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    SignUpService userService;
    
    
    @Test
    @DisplayName("고객 유저 등록 테스트")
    public void signupCustomerTest() {
        //given
        
        UserDto dto = new UserDto(
            "abc@test.com",
            "abc123",
            "김철수",
            null,
            "Man",
            10
        );

        //when
        User user = userService.signupCustomer(dto);

        //then
        then(user.getId()).isNotNull();
        then(user.getUsername()).isEqualTo("김철수");
        then(user.getPassword()).startsWith("{bcrypt}");
        then(user.getAuthorities()).hasSize(1); // Authority 가 1개 인지 검증
        then(user.getAuthorities().stream().findFirst().get().getAuthority()).isEqualTo("ROLE_CUSTOMER");
        then(user.isEnabled()).isTrue();

    }


}