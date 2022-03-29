package example.minitable.service;

import example.minitable.domain.Booking;
import example.minitable.domain.Review;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.ReviewRequest;
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

}
