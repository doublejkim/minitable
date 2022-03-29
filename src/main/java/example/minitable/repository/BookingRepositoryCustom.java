package example.minitable.repository;

import example.minitable.domain.Booking;
import example.minitable.domain.User;
import example.minitable.dto.BookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BookingRepositoryCustom {

    Page<Booking> findBookingListByOwnerUser(User user, Pageable pageable);

    Page<BookingDto> findBookingInfoByCustomerUser(User user, LocalDateTime afterDate, Pageable pageable);


    Booking findBookingByUserEmailAndStoreOwnerEmailAndCriterionDate(
            String userEmail,
            String storeOwnerEmail,
            String criterionDate);



}
