package example.minitable.repository;

import example.minitable.domain.Booking;
import example.minitable.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingRepositoryCustom {

    Page<Booking> findBookingListByUser(User user, Pageable pageable);

    Booking findBookingByUserEmailAndStoreOwnerEmailAndCriterionDate(
            String userEmail,
            String storeOwnerEmail,
            String criterionDate);

}
