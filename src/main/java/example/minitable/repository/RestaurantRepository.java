package example.minitable.repository;

import example.minitable.domain.store.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {

    Optional<Restaurant> findByIdAndRandomNo(Long storeId, String randomNo);
    Optional<Restaurant> findByRandomNo(String randomNo);
}
