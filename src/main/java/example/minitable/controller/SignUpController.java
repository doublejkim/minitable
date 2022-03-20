package example.minitable.controller;

import example.minitable.dto.SignUpRequest;
import example.minitable.dto.UserDto;
import example.minitable.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;


/*    @GetMapping("/login")
    public String login() {
        return "login";
    }*/

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignUpRequest signUpRequest) {

        // Customer  가입일 경우에 authority 를 ROLE_CUSTOMER 로 설정 필요
        UserDto userDto = UserDto.from(signUpRequest);
        userDto.setAuthority("ROLE_CUSTOMER");
        userService.signupCustomer(userDto);

        return "redirect:login";
    }

    /*@GetMapping("/signup-store-owner")
    public String signupStoreOwner() {
        return "signup-store-owner";
    }

    @PostMapping("/signup-store-owner")
    public String signupStoreOwner(UserDto userDto) {

    }*/
}
