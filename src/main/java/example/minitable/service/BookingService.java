package example.minitable.service;

import example.minitable.constant.ErrorCode;
import example.minitable.domain.Booking;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.BookingDto;
import example.minitable.dto.BookingRequest;
import example.minitable.exception.GeneralException;
import example.minitable.repository.BookingRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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

    public boolean existBookingInfo(Long storeId) {

        // 유저 엔티티 획득.
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Store 엔티티 획득.
        Restaurant restaurant = restaurantRepository.findById(storeId).orElse(null);

        if (restaurant == null) {

            log.info("  >>> Not found Store Info with storeId : " + storeId);
            return false;
        }

        // 오늘 날짜에 해당하는 creterion_date 를 설정하여 예약정보가 존재하는지 확인
        // 단순 조회로 0시 기준으로 하루전 조회는 skip
        String criterionDate = LocalDate.now().toString();


        Booking booking = bookingRepository.findByUserAndStoreAndCriterionDate(user, restaurant, criterionDate).orElse(null);

        return (booking != null);

    }

    public Page<BookingDto> getBookingList(Pageable pageable) {

        // ROLE_STORE_OWNER 권한을 가진 유저만 호출가능하므로 SecurityContext 에서 유저정보를 회득 후
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Store 엔티티에서 해당 유저 소유의 Store 를 획득
        // 위에서 획득한 Store(Restaurant) 정보와 오늘 날짜를 조회 조건으로하여 Booking 정보 조회

        Page<Booking> results = bookingRepository.findBookingListByUser(user, pageable);

        return results.<BookingDto>map(BookingDto::from);
    }

    public Page<BookingDto> getBookingListWithStoreOwnerEmail(String email, Pageable pageable) {


         User user = userRepository.findByEmail(email);

         if(user == null) {
             log.warn("     >>> This user is not exist. email : " + email);
             throw new GeneralException(ErrorCode.NOT_FOUND);
         }

        Page<Booking> results = bookingRepository.findBookingListByUser(user, pageable);

        return results.<BookingDto>map(BookingDto::from);

    }
}