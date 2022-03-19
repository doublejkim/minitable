package example.minitable.config;

import example.minitable.domain.User;
import example.minitable.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        // basic authentication filter 명시적 비활성화.
        // 모든 요청에 id/pwd 방식으로만 인증을 시도하는 BasicAuthenticationFilter 를 사용하지 않도록함.
        http.httpBasic().disable();

        // Csrf Attack 을 막기위한 Csrf Filter 적용
        http.csrf();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        // 정적 리소스 ("images.. css..) 는 Spring Security 에서 제외
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        //return super.userDetailsServiceBean();

        // username 에는 email 이 담겨있을꺼라고 일단 추축. 확인 필요
        return (username) -> {

            log.debug("loadUserByUsername : username : " + username);

            User user = userService.findByEmail(username);
            if(user == null) {
                throw new UsernameNotFoundException(username);
            }
            return user;
        };

    }


}
