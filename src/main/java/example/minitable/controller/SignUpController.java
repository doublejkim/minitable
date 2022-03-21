package example.minitable.controller;

import example.minitable.dto.SignUpRequest;
import example.minitable.dto.SignUpStoreOwnerRequest;
import example.minitable.dto.UserDto;
import example.minitable.service.SignUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignUpController {

    private final SignUpService signUpService;


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


        UserDto userDto = UserDto.from(signUpRequest);
        signUpService.signupCustomer(userDto);

        return "redirect:login";
    }

    @GetMapping("/signup-store-owner")
    public String signupStoreOwner() {
        log.debug("signupStoreOwner() !!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return "signup-store-owner";
    }

    @PostMapping("/signup-store-owner")
    public String signupStoreOwner(SignUpStoreOwnerRequest signUpStoreOwnerReq) {

        //현재는 Restaurant 형태의 가게만 존재하므로 Restaurant Dto 자체도 Restaurant 에 해당하는 값이 들어가있으며
        //저장할때  dtype 도 RESTAURANT 로 저장될 것임


        // Store, Restaurant 등록 서비스 호출
        signUpService.signupStoreOwner(signUpStoreOwnerReq);

        // TODO :  서비스 호출후 할 것 없는지 확인 필요

        // 등록후 login 으로 redirectx


        return "redirect:login";
    }
}
