package example.minitable.service;

import example.minitable.constant.ErrorCode;
import example.minitable.domain.Booking;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.BookingRequest;
import example.minitable.exception.GeneralException;
import example.minitable.repository.BookingRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean createBooking(BookingRequest bookingReq) {

        // Store(Restaurant) 정보 확인후 실제 조회 되면 진행 (storeId, randomNo)
        // 존재 하지 않으면 Exception 발생하고 진행 중지
        Restaurant restaurant = restaurantRepository
                .findByRandomNo(bookingReq.getRandomNo())
                .orElseThrow(() -> new GeneralException(ErrorCode.WRONG_STORE_RANDOM_CODE));

        // 유저 엔티티 획득.
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // Booking 정보 등록
        LocalDate nowDate = LocalDate.now();

        Booking booking = new Booking(user, restaurant, nowDate.toString());
        bookingRepository.save(booking);

        return true;
    }
}
