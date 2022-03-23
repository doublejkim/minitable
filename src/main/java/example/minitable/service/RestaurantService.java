package example.minitable.service;

import example.minitable.domain.store.Restaurant;
import example.minitable.dto.StoreDto;
import example.minitable.dto.condition.StoreSearchCondition;
import example.minitable.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Page<StoreDto> getStores(StoreSearchCondition condition, Pageable pageable) {

        return restaurantRepository.getStores(condition, pageable);

    }


}
