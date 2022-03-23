package example.minitable.repository;

import example.minitable.domain.store.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {


}
