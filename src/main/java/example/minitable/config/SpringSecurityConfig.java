package example.minitable.config;

import example.minitable.domain.User;
import example.minitable.jwt.JwtAuthenticationFilter;
import example.minitable.jwt.JwtAuthorizationFilter;
import example.minitable.repository.UserRepository;
import example.minitable.service.SignUpService;
import example.minitable.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        // basic authentication filter ????????? ????????????.
        // ?????? ????????? id/pwd ??????????????? ????????? ???????????? BasicAuthenticationFilter ??? ???????????? ????????????.
        http.httpBasic().disable();

        // Csrf Attack ??? ???????????? Csrf Filter ??????
        http.csrf();
        //http.csrf().disable();

        // jwt ??? ??????????????? session ??? ?????? ?????? ?????????  stateless ????????? ???????????????
        /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // jwt filter
        http.addFilterBefore(
                new JwtAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(
                new JwtAuthorizationFilter(userRepository),
                BasicAuthenticationFilter.class
        );*/

        // authorization
        http.authorizeRequests()
                // /??? /home??? ???????????? ??????
                .antMatchers("/", "/home", "/signup", "/signup-store-owner", "/error", "/hello").permitAll()
                // hello ???????????? USER ?????? ?????? ??????????????? ??????
                .antMatchers(HttpMethod.GET, "/api/review", "/api/stores", "/booking", "/images", "/review/details", "/review", "/stores").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/stores").hasRole("STORE_OWNER")
                .antMatchers(HttpMethod.DELETE, "/api/stores").hasRole("STORE_OWNER")
                .antMatchers(HttpMethod.POST, "/booking",
                        "/review/createReview",
                        "/review/getMyReview",
                        "/review/createReview.do",
                        "/review/modifyReview.do"
                ).hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST,
                        "/booking/callcustomer",
                        "/booking/seatcustomer"
                ).hasRole("STORE_OWNER")
                .antMatchers(
                        "/bookinglist-with-customer",
                        "/api/bookinglist-with-customer"
                ).hasRole("CUSTOMER")
                .antMatchers(
                        "/api/booking/seatcustomer",
                        "/api/bookinglist",
                        "/api/bookinglist-with-email"
                ).hasRole("STORE_OWNER")

                //.antMatchers("/note").hasRole("USER")
                //.antMatchers("/admin").hasRole("ADMIN")
                //.antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                //.antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                //  .anyRequest().authenticated();
                //.antMatchers("/bookinglist-with-customer", "/booking/withcustomer").hasRole("CUSTOMER")
                //.antMatchers("/bookinglist").hasRole("STORE_OWNER")
                .anyRequest().permitAll();  // test ???

        // login
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("email") // Spring Security ?????? ???????????? username(key) ??? ?????? ??????????????????????????? email ??? ????????? ??????
                .defaultSuccessUrl("/")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("=========================================================");
                        log.info("login seuccess !!!!!!!!!!!!!!!!!!!");
                        response.sendRedirect("/");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        log.info("=========================================================");
                        log.info(exception.getMessage());
                        response.sendRedirect("/login");
                    }
                })
                .permitAll(); // ?????? ??????

        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        // ?????? ????????? ("images.. css..) ??? Spring Security ?????? ??????
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        //return super.userDetailsServiceBean();

        return (email) -> {

            log.debug("loadUserByUsername : email : " + email);

            User user = userService.findByEmail(email);
            if(user == null) {
                throw new UsernameNotFoundException(email);
            }
            return user;
        };

    }


}
