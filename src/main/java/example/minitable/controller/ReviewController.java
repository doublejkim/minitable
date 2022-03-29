package example.minitable.controller;

import example.minitable.dto.ReviewRequest;
import example.minitable.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Validated
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /*
    Customer 고객이 My Booking 화면에서 리뷰 작성 요청
     */
    @PostMapping("/review/createReview")
    public ModelAndView createReview(
            String storeId,
            String bookingId
    ) {
        log.debug("     >>> ######################################## POST createReview() !!! storeId : " + storeId + ", bookingId : " + bookingId);
        Map<String, Object> map = new HashMap<>();

        // 차후 필요한 작업이 있으면 여기에 기술
        map.put("storeId", storeId);
        map.put("bookingId", bookingId);

        return new ModelAndView("review/createReviewPage", map);
    }

    /*
    Customer 유저가 신규 리뷰 작성후 신규리뷰 등록 요청
     */
    @PostMapping("/review/createReview.do")
    public String createReviewDo(
            ReviewRequest reviewRequest
    ) {

        log.debug("     >>> ######################################## GET createReviewDo() !!!");
        log.debug("storeId : " + reviewRequest.getStoreId());
        log.debug("bookingId : " + reviewRequest.getBookingId());
        log.debug("title : " + reviewRequest.getReviewTitle());
        log.debug("contents : " + reviewRequest.getReviewContents());
        log.debug("star : " + reviewRequest.getStar());

        if(reviewRequest.getAttachFile()!=null && !reviewRequest.getAttachFile().isEmpty()) {
            log.debug("original File Name : " + reviewRequest.getAttachFile().getOriginalFilename());
        }

        boolean result = reviewService.createReviewDo(reviewRequest);

        return "redirect:/bookinglist-with-customer";
    }
}
