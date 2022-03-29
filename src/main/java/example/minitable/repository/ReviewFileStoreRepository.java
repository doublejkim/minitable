package example.minitable.repository;

import example.minitable.domain.ReviewFileStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewFileStoreRepository extends JpaRepository<ReviewFileStore, Long> {
}
