package example.minitable.repository;

import example.minitable.domain.Booking;
import example.minitable.domain.User;
import example.minitable.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingRepositoryCustom {

    Optional<Booking> findByUserAndStoreAndCriterionDateAndCompleteYn(
            User user,
            Store store,
            String criterionDate,
            String completeYn
    );

    Page<Booking> findByStoreAndCriterionDate(Store store, String criterionDate, Pageable pageable);

    Page<Booking> findByUserAndStoreAndCompleteYnAndForcedCanceledYn(
            User user,
            Store store,
            String completeYn,
            String forcedCanceledYn,
            Pageable pageable
    );

    Page<Booking> findByUserAndCompleteYnAndForcedCanceledYn(
            User user,
            String completeYn,
            String forcedCanceledYn,
            Pageable pageable
    );

}
