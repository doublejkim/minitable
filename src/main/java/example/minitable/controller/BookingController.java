package example.minitable.controller;

import example.minitable.dto.BookingRequest;
import example.minitable.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class BookingController {

    private final BookingService bookingService;


    @GetMapping("/booking")
    public String getBooking() {

        log.debug("     >>> ######################################## GET getBooking() !!!");

        //Map<String, Object> map = new HashMap<>();

        return  "redirect:home";
    }


    @PostMapping("/booking")
    public String booking(@Valid BookingRequest bookingRequest) {


        log.debug("     >>> ######################################## POST booking() !!! name : ");
        //Map<String, Object> map = new HashMap<>();

    // user Id 는 받지말고 storeId 나 randomNO 만 받도록 변경


        bookingService.createBooking(bookingRequest);

        // 차후에 나의 예약 조회화면 을 개발하면 좋을듯. 현재는 skip
        return "redirect:home";
    }
}
