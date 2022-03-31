package example.minitable.controller;

import example.minitable.dto.BookingDto;
import example.minitable.dto.BookingRequest;
import example.minitable.dto.SeatDto;
import example.minitable.service.BookingService;
import example.minitable.service.CallService;
import example.minitable.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final CallService callService;
    private final ReviewService reviewService;


    @GetMapping("/booking")
    public String getBooking() {

        log.debug("     >>> ######################################## GET getBooking() !!!");

        //Map<String, Object> map = new HashMap<>();

        return  "redirect:home";
    }


    @PostMapping("/booking")
    public String createBooking(@Valid BookingRequest bookingRequest) {

        log.debug("     >>> ######################################## POST booking() !!!");
        //Map<String, Object> map = new HashMap<>();

        // user Id 는 받지말고 storeId 나 randomNO 만 받도록 변경
        bookingService.createBooking(bookingRequest);

        // 차후에 나의 예약 조회화면 을 개발하면 좋을듯. 현재는 skip
        return "redirect:home";
    }

    @GetMapping("/bookinglist-with-customer")
    public ModelAndView getBookingListWithCustomer(Pageable pageable) {

        Map<String, Object> map = new HashMap<>();

        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        Page<BookingDto> resultPage = bookingService.getBookingListWithCustomer(pageable);

        map.put("bookinglist", resultPage);

        return new ModelAndView("booking/withcustomer", map);
    }



    /**
     * Store Owner 유저가 본인 점포 기준으로 등록된 예약 현황 조회 컨트롤러
     * @param pageable
     * @return
     */
    @GetMapping("/bookinglist")
    public ModelAndView getBookingList(Pageable pageable) {

        log.debug("     >>> ######################################## GET booking() !!!");
        Map<String, Object> map = new HashMap<>();


        int page = (pageable.getPageNumber() ==0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10); // @@@

        Page<BookingDto> resultPage = bookingService.getBookingList(pageable);
        map.put("bookinglist", resultPage);

        return new ModelAndView("booking/index", map);
    }

    /**
     * 고객 착석 완료 수행 컨트롤러
     * @param userEmail
     * @param criterionDate
     * @return
     */
    @PostMapping("/booking/seatcustomer")
    public String seatCustomer(
            @NotEmpty String userEmail,
            @NotEmpty String criterionDate
    ) {
        SeatDto resultDto = bookingService.seatCustomer(userEmail, criterionDate);

        return "redirect:/bookinglist";
    }

    @PostMapping("/booking/callcustomer")
    public String callCustomer(
            @NotEmpty String userEmail,
            Long bookingId
    ) {

        System.out.println("    >>> ######################################## callCustomer start. meail");
        System.out.println("userEmail = " + userEmail);
        System.out.println("bookingId = " + bookingId);

        boolean result = callService.callToCustomer(userEmail, bookingId);

        System.out.println("    >>> ######################################## callCustomer end. result : " + result);
        return "redirect:/bookinglist";
    }
}
