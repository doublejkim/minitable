package example.minitable.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class StoreReviewResponse {

    private Long reviewId;
    private String reviewerEmail;
    private String reviewerName;
    private String reviewTitle;
    private String reviewText;
    private int star;
    private String bookingDate;

    @QueryProjection
    public StoreReviewResponse(
            Long reviewId,
            String reviewerEmail,
            String reviewerName,
            String reviewTitle,
            String reviewText,
            int star,
            LocalDateTime bookingDate
    ) {
        this.reviewId = reviewId;
        this.reviewerEmail = reviewerEmail;
        this.reviewerName = reviewerName;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.star = star;
        this.bookingDate = bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
