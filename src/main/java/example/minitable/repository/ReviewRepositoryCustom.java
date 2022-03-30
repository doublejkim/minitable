package example.minitable.repository;

import example.minitable.dto.StoreReviewDetailsDto;
import example.minitable.dto.StoreReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepositoryCustom {

    Page<StoreReviewResponse> findStoreReviewListByStoreId(Long storeId, int untilDay, Pageable pageable);

    Optional<StoreReviewDetailsDto> findStoreReviewDetails(Long review_id);
}
