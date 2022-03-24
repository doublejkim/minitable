package example.minitable.jwt;

import example.minitable.domain.User;
import example.minitable.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * JWT를 이용한 인증
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String token = null;
        try {

            // cookie 에서 JWT token 획득..
            // JWT-AUTHENTICATION 이라는 이름의 쿠기가 있다면 그 해당 값을 리턴 받음. 없다면 null
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

        } catch (Exception ignored) {
        }
        if (token != null) {

            try {


                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // 예외가 발생할경우 JWT-AUTHENTICATION 이름의 쿠키는 초기화
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
     * User가 없다면 null
     */
    private Authentication getUsernamePasswordAuthenticationToken(String token) {

        String email = JwtUtils.getUserEmail(token);

        if (email != null) {

            User user = userRepository.findByEmail(email); // email 로 유저 조회

            return new UsernamePasswordAuthenticationToken(
                    user, // principal
                    null,
                    user.getAuthorities()
            );
        }

        return null; // 유저가 존재하지 않으면 null 리턴
    }
}