package example.minitable.controller.api;

import example.minitable.dto.ApiDataResponse;
import example.minitable.dto.StoreReviewResponse;
import example.minitable.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/review/{storeId}")
    public ApiDataResponse<Page<StoreReviewResponse>> getStoreReviewList(
            @PathVariable Long storeId,
            Pageable pageable
    ) {


        log.debug("     >>> ######################################## GET API getStoreReviewList() !!!");
        log.debug("storeId : {}", storeId);

        Page<StoreReviewResponse> resultPage = reviewService.getStoreReviewList(storeId, pageable);


        return ApiDataResponse.of(resultPage);
    }
}
