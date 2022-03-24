package example.minitable.service;

import example.minitable.domain.Booking;
import example.minitable.domain.User;
import example.minitable.domain.store.Restaurant;
import example.minitable.dto.BookingRequest;
import example.minitable.repository.BookingRepository;
import example.minitable.repository.RestaurantRepository;
import example.minitable.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @Mock BookingRepository bookingRepository;
    @Mock RestaurantRepository restaurantRepository;
    @Mock UserRepository userRepository;

    @Test
    void createBooking() {

        //gien
        /*
        public Restaurant(User user, String storeName, String address, String phone, String startTime, String endTime,
                      String startBreaktime, String endBreaktime, String pestControlYn, String lastPestControlDate)
         */
        User user = new User("ROLE_STORE_OWNER", "zzz@test.com", "zzz",
                "zzz_owner", null, "man", 50);

        Restaurant restaurant = new Restaurant(user, "zzzStore", "zzzAddress", "1111-1111",
                "09", "20", null, null, "N", null);

        given(restaurantRepository.findByIdAndRandomNo(anyLong(), anyString())).willReturn(Optional.of(restaurant));

        given(userRepository.getById(anyLong())).willReturn(user);

        //LocalDate nowDate = LocalDate.now();
        //Booking booking = new Booking(user, restaurant, nowDate.toString());
        //given(bookingRepository.save(booking)).wil;


        //when
        BookingRequest bookingReq = new BookingRequest(1L, "1234", 1L);
        boolean result = bookingService.createBooking(bookingReq);

        assertThat(result).isTrue();

        //then

    }
}