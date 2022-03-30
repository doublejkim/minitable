package example.minitable.repository;

import example.minitable.domain.Review;
import example.minitable.domain.ReviewFileStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewFileStoreRepository extends JpaRepository<ReviewFileStore, Long> {

    @Query("select rfs from ReviewFileStore rfs where rfs.id = :reviewId")
    List<ReviewFileStore> findByReviewId(@Param("reviewId") Long reviewId);
}
