package example.minitable.service;

import example.minitable.constant.ErrorCode;
import example.minitable.domain.Booking;
import example.minitable.domain.Review;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.ReviewRequest;
import example.minitable.dto.ReviewResponse;
import example.minitable.exception.GeneralException;
import example.minitable.repository.BookingRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public boolean createReviewDo(ReviewRequest reviewRequest) {

        // 유저 정보 획득
        // CUSTOMER 권한 유저 정보
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Store(Restaurant) 정보 획득
        Restaurant restaurant = restaurantRepository.findById(reviewRequest.getStoreId()).orElse(null);
        if(restaurant==null) {
            return false;  // view 에서 응답이 필요하므로 일단 false 리턴. 차후 api 호출이 되면 exception throw 하자
        }

        // Booking 정보 획득
        Booking booking = bookingRepository.findById(reviewRequest.getBookingId()).orElse(null);
        if (booking == null) {
            return false;
        }

        Review review = new Review(user, booking, restaurant, reviewRequest);
        reviewRepository.save(review);

        return true;
    }

    /*
    이전에 작성했던 리뷰 내용 획득 하는 서비스 메소드
     */
    public ReviewResponse getMyReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new GeneralException(ErrorCode.NOT_FOUND));

        return ReviewResponse.from(review);

    }

    /**
     * 등록했던 리뷰 내용 수정
     */
    @Transactional
    public boolean modifyReviewDo(ReviewRequest reviewRequest) {

        Review review = reviewRepository.getById(reviewRequest.getReviewId());

        review.setReviewTitle(reviewRequest.getReviewTitle());
        review.setReviewText(reviewRequest.getReviewContents());
        review.setStar(reviewRequest.getStar());

        return true;
    }

}
