package example.minitable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Password Encoder 만을 위한 Config 클래스
 * Spring Security Config 클래스 내부에서 Service 를 사용할 경우
 * Password Encoder 도 사용되기때문에 순환참조가 발생할 수 있어 별도로 분리
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
