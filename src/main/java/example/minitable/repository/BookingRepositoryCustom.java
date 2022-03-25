package example.minitable.repository;

import example.minitable.domain.Booking;
import example.minitable.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingRepositoryCustom {

    Page<Booking> findBookingListByUser(User user, Pageable pageable);

}
