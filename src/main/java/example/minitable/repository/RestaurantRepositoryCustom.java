package example.minitable.repository;

import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantRepositoryCustom {


    public Page<StoreDto> getStores(StoreSearchCondition condition, Pageable pageable);
}
